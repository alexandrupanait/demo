package com.example.demo.account;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Exposes the "logged in as X / Company [deconectare]" indicator to every
 * page (mirrors the old site's site-wide _top.html.erb ".Registered" block,
 * not just the ralonline portal) - without forcing a session for visitors
 * who never logged in.
 */
@ControllerAdvice
public class LoggedInAdvice {

    @ModelAttribute("userNameLogat")
    public String userNameLogat(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (String) session.getAttribute("userName");
    }

    @ModelAttribute("clientFirmaLogat")
    public String clientFirmaLogat(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (String) session.getAttribute("clientFirma");
    }
}
