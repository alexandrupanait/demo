package com.example.demo.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.catalog.PricingService;
import com.example.demo.catalog.StocuriSiteView;
import com.example.demo.catalog.StocuriSiteViewRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

    private static final Integer ANONYMOUS_CLIENT = 0;
    private static final String SESSION_KEY = "cart";

    private final StocuriSiteViewRepository stocuriSiteViewRepository;
    private final PricingService pricingService;

    public CartService(StocuriSiteViewRepository stocuriSiteViewRepository, PricingService pricingService) {
        this.stocuriSiteViewRepository = stocuriSiteViewRepository;
        this.pricingService = pricingService;
    }

    public Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_KEY, cart);
        }
        return cart;
    }

    /** Doesn't create a session/cart for visitors who never touched the cart - just reports 0. */
    public int itemCount(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(SESSION_KEY);
        if (cart == null) {
            return 0;
        }
        return cart.getItems().values().stream().mapToInt(Integer::intValue).sum();
    }

    /** Resolves each cart line against live product data - items no longer online/found are silently dropped. */
    public List<CartLine> resolveLines(Cart cart, Integer clientId, BigDecimal discountClient) {
        List<CartLine> linii = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
            Optional<StocuriSiteView> produs = stocuriSiteViewRepository.findByIdAndIdclientAndOnlineTrue(entry.getKey(), ANONYMOUS_CLIENT);
            if (produs.isEmpty()) {
                continue;
            }
            // Fara TVA - this is the internal line-item price the real order/invoice
            // tables expect (see LegacyOrderService, which adds its own TVA on top).
            BigDecimal pretUnitar = pricingService.computePriceFaraTva(produs.get(), clientId, discountClient);
            int cantitate = entry.getValue();
            BigDecimal subtotal = pretUnitar.multiply(BigDecimal.valueOf(cantitate));
            linii.add(new CartLine(produs.get(), cantitate, pretUnitar, subtotal));
        }
        return linii;
    }

    public BigDecimal total(List<CartLine> linii) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartLine linie : linii) {
            total = total.add(linie.getSubtotal());
        }
        return total;
    }

    // Sums the same way LegacyOrderService computes its real total (per-line
    // TVA, rounded, then summed) - so the checkout preview the customer sees
    // matches the actual order placed to the cent, not just approximately.
    public BigDecimal totalCuTva(List<CartLine> linii) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartLine linie : linii) {
            total = total.add(linie.getSubtotalCuTva());
        }
        return total;
    }
}
