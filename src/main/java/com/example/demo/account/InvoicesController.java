package com.example.demo.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class InvoicesController {

    private final InvoiceRepository invoiceRepository;

    public InvoicesController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping("/ralonline/invoices")
    public String index(HttpSession session, Model model) {
        Integer clientId = (Integer) session.getAttribute("clientId");
        model.addAttribute("facturi", invoiceRepository.findByIdClientOrderByDataDesc(clientId));
        return "ralonline/invoices";
    }
}
