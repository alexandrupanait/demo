package com.example.demo.cart;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.account.ClientAccount;
import com.example.demo.account.ClientAccountRepository;

import jakarta.servlet.http.HttpSession;

/**
 * Places the real order, guarded by the existing RalonlineAuthInterceptor
 * since this lives under /ralonline/shopping_cart/** - matches the old site
 * requiring login only to actually place an order, not to browse/edit the
 * cart (the combined cart+checkout page itself is /cart, CartController).
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

    private final CartService cartService;
    private final ClientAccountRepository clientAccountRepository;
    private final LegacyOrderService legacyOrderService;

    public CheckoutController(CartService cartService, ClientAccountRepository clientAccountRepository,
            LegacyOrderService legacyOrderService) {
        this.cartService = cartService;
        this.clientAccountRepository = clientAccountRepository;
        this.legacyOrderService = legacyOrderService;
    }

    // The checkout form is now part of the combined /cart page - keep this
    // around only so any old bookmarked/linked URL still lands somewhere sane.
    @GetMapping("/checkout")
    public String checkoutForm() {
        return "redirect:/cart";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String emailClient, @RequestParam String adresaLivrare,
            @RequestParam(required = false) String comentarii, @RequestParam String modPlata,
            @RequestParam String modLivrare, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getOrCreateCart(session);
        Integer clientId = (Integer) session.getAttribute("clientId");
        BigDecimal discount = (BigDecimal) session.getAttribute("clientDiscount");
        List<CartLine> linii = cartService.resolveLines(cart, clientId, discount);
        if (linii.isEmpty()) {
            return "redirect:/cart";
        }

        ClientAccount cont = clientAccountRepository.findById(clientId).orElseThrow();
        Integer idAutor = (Integer) session.getAttribute("userId");
        String numePersoana = (String) session.getAttribute("userName");

        // Totals are always recomputed server-side from live product prices,
        // inside LegacyOrderService - never trust anything the client submitted.
        ComandaPlasata comanda = legacyOrderService.plaseazaComanda(cont, idAutor, numePersoana, emailClient,
                adresaLivrare, comentarii, modPlata, modLivrare, linii, CartService.TRANSPORT_FEE);

        cart.clear();

        // Redirect (not render directly) so refreshing the confirmation page
        // never re-submits the order, and so the header's cart badge picks up
        // the now-empty cart on this fresh request instead of showing the
        // stale pre-clear count (ModelAttribute advice runs before this
        // handler, so rendering straight from here would show "1 produs in
        // cos" even though the cart was already cleared).
        redirectAttributes.addFlashAttribute("comandaId", comanda.getComandaId());
        redirectAttributes.addFlashAttribute("total", comanda.getTotal());
        return "redirect:/ralonline/shopping_cart/order-placed";
    }

    @GetMapping("/order-placed")
    public String orderPlaced() {
        return "ralonline/order-confirmation";
    }
}
