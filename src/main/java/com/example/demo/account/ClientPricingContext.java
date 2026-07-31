package com.example.demo.account;

import java.math.BigDecimal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Reads the logged-in client's id and discount percentage from the session
 * (set by AuthController at login), without forcing a session for visitors
 * who never logged in - both null for an anonymous visitor.
 */
public class ClientPricingContext {

    private ClientPricingContext() {
    }

    public static Integer clientId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (Integer) session.getAttribute("clientId");
    }

    public static BigDecimal discount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (BigDecimal) session.getAttribute("clientDiscount");
    }
}
