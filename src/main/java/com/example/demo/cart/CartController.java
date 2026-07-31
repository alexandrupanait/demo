package com.example.demo.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.account.ClientAccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * The real site's combined cart+checkout page (shopping_cart/index.html.erb):
 * cart contents, payment/delivery mode, weight/exchange info, and the
 * shipping/comments form all together. Placing the order itself still lives
 * under /ralonline/shopping_cart/place-order (CheckoutController), guarded
 * by the login interceptor - matches the real site, which lets anyone browse
 * and edit the cart here but only shows the actual "Plaseaza comanda" button
 * once logged in.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private static final BigDecimal VAT_RATE = new BigDecimal("0.21");

    private final CartService cartService;
    private final ClientAccountRepository clientAccountRepository;
    private final FinanciarRateRepository financiarRateRepository;

    public CartController(CartService cartService, ClientAccountRepository clientAccountRepository,
            FinanciarRateRepository financiarRateRepository) {
        this.cartService = cartService;
        this.clientAccountRepository = clientAccountRepository;
        this.financiarRateRepository = financiarRateRepository;
    }

    @PostMapping("/add")
    public String add(@RequestParam Integer produsId, HttpSession session) {
        cartService.getOrCreateCart(session).add(produsId);
        return "redirect:/cart";
    }

    /** One shared "Actualizeaza cantitatile" submit updates every row at once - mirrors the real page's single-form table. */
    @PostMapping("/update-all")
    public String updateAll(HttpServletRequest request, HttpSession session) {
        Cart cart = cartService.getOrCreateCart(session);
        request.getParameterMap().forEach((key, values) -> {
            if (key.startsWith("qty_") && values.length > 0) {
                try {
                    Integer produsId = Integer.valueOf(key.substring(4));
                    int cantitate = Integer.parseInt(values[0]);
                    cart.setQuantity(produsId, cantitate);
                } catch (NumberFormatException ignored) {
                    // malformed field name/value - skip it rather than fail the whole update
                }
            }
        });
        return "redirect:/cart";
    }

    @PostMapping("/remove-bulk")
    public String removeSelected(@RequestParam(required = false) List<Integer> toDel, HttpSession session) {
        Cart cart = cartService.getOrCreateCart(session);
        if (toDel != null) {
            cart.removeAll(toDel);
        }
        return "redirect:/cart";
    }

    @GetMapping
    public String view(HttpSession session, Model model) {
        Cart cart = cartService.getOrCreateCart(session);
        Integer clientId = (Integer) session.getAttribute("clientId");
        BigDecimal discount = (BigDecimal) session.getAttribute("clientDiscount");
        List<CartLine> linii = cartService.resolveLines(cart, clientId, discount);

        BigDecimal transportTva = CartService.TRANSPORT_FEE.multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bazaTotal = cartService.total(linii).add(CartService.TRANSPORT_FEE);
        BigDecimal grandTotal = cartService.totalCuTva(linii).add(CartService.TRANSPORT_FEE).add(transportTva);
        BigDecimal tvaTotal = grandTotal.subtract(bazaTotal);

        model.addAttribute("linii", linii);
        model.addAttribute("transportFaraTva", CartService.TRANSPORT_FEE);
        model.addAttribute("transportTva", transportTva);
        model.addAttribute("bazaTotal", bazaTotal);
        model.addAttribute("tvaTotal", tvaTotal);
        model.addAttribute("total", grandTotal);
        model.addAttribute("toateInStoc", linii.stream().allMatch(CartLine::isInStoc));

        model.addAttribute("cont", clientId != null ? clientAccountRepository.findById(clientId).orElse(null) : null);
        model.addAttribute("cursUsd", rate("COPUSD"));
        model.addAttribute("cursEur", rate("COPEUR"));

        return "cart/view";
    }

    private BigDecimal rate(String nume) {
        return financiarRateRepository.findByNume(nume).map(FinanciarRate::getValoare).orElse(BigDecimal.ONE);
    }
}
