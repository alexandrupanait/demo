package com.example.demo.cart;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/** Makes the cart item count available to every page (for the header badge) without forcing a session for visitors who never touched the cart. */
@ControllerAdvice
public class CartAdvice {

    private final CartService cartService;

    public CartAdvice(CartService cartService) {
        this.cartService = cartService;
    }

    @ModelAttribute("cartItemCount")
    public int cartItemCount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return 0;
        }
        return cartService.itemCount(session);
    }
}
