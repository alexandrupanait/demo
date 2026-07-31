package com.example.demo.content;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class SolutionsController {

    private final SolutieRepository solutieRepository;

    public SolutionsController(SolutieRepository solutieRepository) {
        this.solutieRepository = solutieRepository;
    }

    @GetMapping("/solutions")
    public String all(Model model) {
        List<Solutie> solutii = solutieRepository.findByOnlineTrueOrderByOrdineAsc();
        if (solutii.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("solutii", solutii);
        model.addAttribute("solutie", solutii.get(0));
        return "solutions/all";
    }

    @GetMapping("/solutions/{id}")
    public String details(@PathVariable Short id, Model model) {
        Solutie solutie = solutieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("solutii", solutieRepository.findByOnlineTrueOrderByOrdineAsc());
        model.addAttribute("solutie", solutie);
        return "solutions/all";
    }
}
