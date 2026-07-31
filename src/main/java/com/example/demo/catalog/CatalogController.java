package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CatalogController {

    private static final String ATTRIBUTE_PARAM_PREFIX = "attr_";

    private final CatalogService catalogService;
    private final ProductFilterService productFilterService;
    private final ProductDetailService productDetailService;

    public CatalogController(CatalogService catalogService, ProductFilterService productFilterService,
            ProductDetailService productDetailService) {
        this.catalogService = catalogService;
        this.productFilterService = productFilterService;
        this.productDetailService = productDetailService;
    }

    @GetMapping("/produse/{id}")
    public String produsDetalii(@PathVariable Integer id, Model model) {
        ProductDetail detail = productDetailService.getProductDetail(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("detaliu", detail);
        model.addAttribute("categoryTree", catalogService.getCategoryTree());
        model.addAttribute("selectedCategorie", detail.getProdus().getIdCategorie());
        return "produse/detail";
    }

    @GetMapping("/produse")
    public String produse(@RequestParam(required = false) Integer categorie,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String nume,
            @RequestParam(required = false) String producator,
            @RequestParam(required = false) Boolean disponibile,
            @RequestParam(required = false) BigDecimal pretMin,
            @RequestParam(required = false) BigDecimal pretMax,
            HttpServletRequest request,
            Model model) {

        ProductFilter filter = new ProductFilter();
        filter.setSort(sort);
        filter.setNameFilter(nume);
        filter.setProducatorCod(producator);
        filter.setDoarDisponibile(Boolean.TRUE.equals(disponibile));
        filter.setPretMin(pretMin);
        filter.setPretMax(pretMax);
        filter.setActiveAttributes(extractActiveAttributes(request));

        ProductListingResult result = productFilterService.listProducts(categorie, filter);

        model.addAttribute("rezultat", result);
        model.addAttribute("filtru", filter);
        model.addAttribute("categoryTree", catalogService.getCategoryTree());
        model.addAttribute("selectedCategorie", categorie);
        return "produse/list";
    }

    private Map<String, Set<String>> extractActiveAttributes(HttpServletRequest request) {
        Map<String, Set<String>> active = new LinkedHashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (key.startsWith(ATTRIBUTE_PARAM_PREFIX) && values.length > 0) {
                String attributeName = key.substring(ATTRIBUTE_PARAM_PREFIX.length());
                active.put(attributeName, new LinkedHashSet<>(java.util.Arrays.asList(values)));
            }
        });
        return active;
    }
}
