package com.example.demo.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.account.ClientAccountRepository;

import jakarta.servlet.http.HttpSession;

/**
 * Real checkout, guarded by the existing RalonlineAuthInterceptor since this
 * lives under /ralonline/shopping_cart/** - matches the old site requiring
 * login only at checkout, not for browsing/adding to cart.
 */
@Controller
@RequestMapping("/ralonline/shopping_cart")
public class CheckoutController {

    // Simplified flat fee - the real transport-cost lookup table is a deliberate
    // simplification dropped from this pass, see the Phase 4 plan.
    private static final BigDecimal TRANSPORT_FEE = new BigDecimal("15.00");

    private final CartService cartService;
    private final ClientAccountRepository clientAccountRepository;
    private final ComandaWebJavaRepository comandaWebJavaRepository;
    private final ContinutComandaWebJavaRepository continutComandaWebJavaRepository;

    public CheckoutController(CartService cartService, ClientAccountRepository clientAccountRepository,
            ComandaWebJavaRepository comandaWebJavaRepository, ContinutComandaWebJavaRepository continutComandaWebJavaRepository) {
        this.cartService = cartService;
        this.clientAccountRepository = clientAccountRepository;
        this.comandaWebJavaRepository = comandaWebJavaRepository;
        this.continutComandaWebJavaRepository = continutComandaWebJavaRepository;
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
    public String placeOrder(@RequestParam String numeClient, @RequestParam String emailClient,
            @RequestParam(required = false) String telefonClient, @RequestParam String adresaLivrare,
            @RequestParam String modPlata, HttpSession session, Model model) {
        Cart cart = cartService.getOrCreateCart(session);
        List<CartLine> linii = cartService.resolveLines(cart);
        if (linii.isEmpty()) {
            return "redirect:/cart";
        }

        // Totals are always recomputed server-side from live product prices -
        // never trust anything the client submitted for pricing.
        BigDecimal total = cartService.total(linii).add(TRANSPORT_FEE);

        ComandaWebJava comanda = new ComandaWebJava();
        comanda.setIdClient((Integer) session.getAttribute("clientId"));
        comanda.setNumeClient(numeClient);
        comanda.setEmailClient(emailClient);
        comanda.setTelefonClient(telefonClient);
        comanda.setAdresaLivrare(adresaLivrare);
        comanda.setTransport("Curier");
        comanda.setModPlata(modPlata);
        comanda.setTotal(total);
        comanda.setCreatLa(LocalDateTime.now());
        comanda = comandaWebJavaRepository.save(comanda);

        for (CartLine linie : linii) {
            ContinutComandaWebJava item = new ContinutComandaWebJava();
            item.setComandaId(comanda.getId());
            item.setProdusId(linie.getProdus().getId());
            item.setProdusCod(linie.getProdus().getCod());
            item.setProdusNume(linie.getProdus().getNumeAfisat());
            item.setCantitate(linie.getCantitate());
            item.setPretUnitar(linie.getPretUnitar());
            item.setValoare(linie.getSubtotal());
            continutComandaWebJavaRepository.save(item);
        }

        cart.clear();
        model.addAttribute("comandaId", comanda.getId());
        model.addAttribute("total", total);
        return "ralonline/order-confirmation";
    }
}
