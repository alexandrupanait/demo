package com.example.demo.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrdersController {

    private final ClientAccountRepository clientAccountRepository;
    private final AxaptaStatisticsClient axaptaStatisticsClient;

    public OrdersController(ClientAccountRepository clientAccountRepository, AxaptaStatisticsClient axaptaStatisticsClient) {
        this.clientAccountRepository = clientAccountRepository;
        this.axaptaStatisticsClient = axaptaStatisticsClient;
    }

    @GetMapping("/ralonline/orders")
    public String index(HttpSession session, Model model) {
        Integer clientId = (Integer) session.getAttribute("clientId");
        String firma = clientAccountRepository.findById(clientId).map(ClientAccount::getFirma).orElse("");

        model.addAttribute("continut", axaptaStatisticsClient.fetchOrders(firma).orElse(null));
        return "ralonline/orders";
    }
}
