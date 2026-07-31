package com.example.demo.content;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class SuppliersController {

    private final ProducatorRepository producatorRepository;

    public SuppliersController(ProducatorRepository producatorRepository) {
        this.producatorRepository = producatorRepository;
    }

    @GetMapping("/suppliers")
    public String all(Model model) {
        model.addAttribute("furnizori", producatorRepository.findByOnlineTrueOrderByOrdineWebAsc());
        return "suppliers/all";
    }

    @GetMapping("/suppliers/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Producator producator = producatorRepository.findById(id)
                .filter(p -> Boolean.TRUE.equals(p.getOnline()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("furnizor", producator);
        return "suppliers/details";
    }
}
