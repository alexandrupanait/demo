package com.example.demo.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/produse";
    }

    @GetMapping("/produse")
    public String produse(@RequestParam(required = false) Integer categorie, Model model) {
        model.addAttribute("produse", catalogService.listOnlineProducts(categorie));
        model.addAttribute("categoryTree", catalogService.getCategoryTree());
        model.addAttribute("selectedCategorie", categorie);
        return "produse/list";
    }
}
