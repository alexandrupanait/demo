package com.example.demo.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String add(@RequestParam Integer produsId, HttpSession session) {
        cartService.getOrCreateCart(session).add(produsId);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer produsId, @RequestParam int cantitate, HttpSession session) {
        cartService.getOrCreateCart(session).setQuantity(produsId, cantitate);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Integer produsId, HttpSession session) {
        cartService.getOrCreateCart(session).remove(produsId);
        return "redirect:/cart";
    }

    @GetMapping
    public String view(HttpSession session, Model model) {
        Cart cart = cartService.getOrCreateCart(session);
        var linii = cartService.resolveLines(cart);
        model.addAttribute("linii", linii);
        model.addAttribute("total", cartService.total(linii));
        return "cart/view";
    }
}
