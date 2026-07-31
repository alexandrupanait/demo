package com.example.demo.cart;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.account.ClientAccount;
import com.example.demo.account.ClientAccountRepository;

import jakarta.servlet.http.HttpSession;

/**
 * Real checkout, guarded by the existing RalonlineAuthInterceptor since this
 * lives under /ralonline/shopping_cart/** - matches the old site requiring
 * login only at checkout, not for browsing/adding to cart.
 *
 * Writes real rows into the legacy order tables (jurnalvanzari_web/
 * comenzi_web/...) via LegacyOrderService - the user chose this over the
 * earlier isolated comenzi_web_java tables so real checkout could be tested,
 * accepting that it syncs into Axapta. See orders_write_real_tables_decision
 * memory.
 */
@Controller
@RequestMapping("/ralonline/shopping_cart")
public class CheckoutController {

    // Simplified flat fee - the real transport-cost lookup table is a deliberate
    // simplification dropped from this pass, see the Phase 4 plan.
    private static final BigDecimal TRANSPORT_FEE = new BigDecimal("15.00");

    private final CartService cartService;
    private final ClientAccountRepository clientAccountRepository;
    private final LegacyOrderService legacyOrderService;

    public CheckoutController(CartService cartService, ClientAccountRepository clientAccountRepository,
            LegacyOrderService legacyOrderService) {
        this.cartService = cartService;
        this.clientAccountRepository = clientAccountRepository;
        this.legacyOrderService = legacyOrderService;
    }

    @GetMapping("/checkout")
    public String checkoutForm(HttpSession session, Model model) {
        Cart cart = cartService.getOrCreateCart(session);
        List<CartLine> linii = cartService.resolveLines(cart);
        if (linii.isEmpty()) {
            return "redirect:/cart";
        }

        Integer clientId = (Integer) session.getAttribute("clientId");
        BigDecimal subtotal = cartService.total(linii);

        model.addAttribute("linii", linii);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("transport", TRANSPORT_FEE);
        model.addAttribute("total", subtotal.add(TRANSPORT_FEE));
        model.addAttribute("cont", clientAccountRepository.findById(clientId).orElse(null));
        return "ralonline/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String emailClient, @RequestParam String adresaLivrare,
            @RequestParam String modPlata, HttpSession session, Model model) {
        Cart cart = cartService.getOrCreateCart(session);
        List<CartLine> linii = cartService.resolveLines(cart);
        if (linii.isEmpty()) {
            return "redirect:/cart";
        }

        Integer clientId = (Integer) session.getAttribute("clientId");
        ClientAccount cont = clientAccountRepository.findById(clientId).orElseThrow();
        Integer idAutor = (Integer) session.getAttribute("userId");
        String numePersoana = (String) session.getAttribute("userName");

        // Totals are always recomputed server-side from live product prices,
        // inside LegacyOrderService - never trust anything the client submitted.
        ComandaPlasata comanda = legacyOrderService.plaseazaComanda(cont, idAutor, numePersoana, emailClient,
                adresaLivrare, modPlata, linii, TRANSPORT_FEE);

        cart.clear();
        model.addAttribute("comandaId", comanda.getComandaId());
        model.addAttribute("total", comanda.getTotal());
        return "ralonline/order-confirmation";
    }
}
