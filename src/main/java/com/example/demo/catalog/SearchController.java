package com.example.demo.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private final ProductFilterService productFilterService;
    private final CatalogService catalogService;

    public SearchController(ProductFilterService productFilterService, CatalogService catalogService) {
        this.productFilterService = productFilterService;
        this.catalogService = catalogService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "search_key", required = false) String searchKey, Model model) {
        String query = searchKey == null ? "" : searchKey.trim();
        model.addAttribute("query", query);

        if (query.length() < 3) {
            model.addAttribute("eroare", "Cheia de cautare nu este valida. Introduceti cel putin 3 caractere.");
        } else {
            model.addAttribute("rezultat", productFilterService.search(query));
        }

        model.addAttribute("categoryTree", catalogService.getCategoryTree());
        model.addAttribute("selectedCategorie", null);
        return "search/result";
    }
}
