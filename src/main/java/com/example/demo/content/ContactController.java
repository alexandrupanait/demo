package com.example.demo.content;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    private final ContentBlockRepository contentBlockRepository;

    public ContactController(ContentBlockRepository contentBlockRepository) {
        this.contentBlockRepository = contentBlockRepository;
    }

    @GetMapping("/contact")
    public String all(Model model) {
        addContentBlocks(model);
        return "contact/all";
    }

    // No SMTP is configured in this environment, so the form doesn't actually
    // send anything yet - it just confirms receipt locally, mirroring the
    // old site's flash message without the real ERPMailer delivery.
    @PostMapping("/contact/sendmail")
    public String sendmail(@RequestParam String nume, @RequestParam String email,
            @RequestParam(required = false) String phone, @RequestParam String message, Model model) {
        addContentBlocks(model);
        model.addAttribute("mesajTrimis", true);
        return "contact/all";
    }

    private void addContentBlocks(Model model) {
        model.addAttribute("continut", contentBlockRepository.findByNameAndActiveTrue("contact.content").orElse(null));
        model.addAttribute("directii", contentBlockRepository.findByNameAndActiveTrue("contact.directions_content").orElse(null));
    }
}
