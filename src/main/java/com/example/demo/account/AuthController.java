package com.example.demo.account;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ralonline")
public class AuthController {

    private final ClientUserRepository clientUserRepository;
    private final ClientAccountRepository clientAccountRepository;

    public AuthController(ClientUserRepository clientUserRepository, ClientAccountRepository clientAccountRepository) {
        this.clientUserRepository = clientUserRepository;
        this.clientAccountRepository = clientAccountRepository;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "ralonline/login";
    }

    // Compares the submitted password directly against the stored plaintext
    // value - no hashing, no DB write. See the Phase 3 plan for why: the old
    // site itself stores passwords in plaintext, and migrating to a proper
    // hash is a separate, explicitly deferred decision.
    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String utilizator, @RequestParam String parola,
            HttpSession session, Model model) {
        Optional<ClientUser> user = clientUserRepository.findFirstByUtilizatorAndActivTrueOrderByIdAsc(utilizator);
        if (user.isEmpty() || !Objects.equals(user.get().getParola(), parola)) {
            model.addAttribute("eroareLogin", true);
            return "ralonline/login";
        }

        ClientUser clientUser = user.get();
        session.setAttribute("userId", clientUser.getId());
        session.setAttribute("userName", clientUser.getNumeComplet());
        session.setAttribute("clientId", clientUser.getIdClient());
        clientAccountRepository.findById(clientUser.getIdClient()).ifPresent(cont -> {
            session.setAttribute("clientFirma", cont.getFirma());
            session.setAttribute("clientDiscount", cont.getProcent());
        });
        // Mirrors the real site exactly: login always lands on the account
        // details page, not a dashboard - see ralonline_controller.rb#authenticate
        // (the alternative "overview" redirect is dead/commented-out code there).
        return "redirect:/ralonline/accounts";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/ralonline/login";
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        Integer clientId = (Integer) session.getAttribute("clientId");
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("cont", clientAccountRepository.findById(clientId).orElse(null));
        return "ralonline/index";
    }
}
