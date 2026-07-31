package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.account.ClientPricingContext;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CatalogController {

    private static final String ATTRIBUTE_PARAM_PREFIX = "attr_";

    private final CatalogService catalogService;
    private final ProductFilterService productFilterService;
    private final ProductDetailService productDetailService;
    private final DriverFilesService driverFilesService;

    public CatalogController(CatalogService catalogService, ProductFilterService productFilterService,
            ProductDetailService productDetailService, DriverFilesService driverFilesService) {
        this.catalogService = catalogService;
        this.productFilterService = productFilterService;
        this.productDetailService = productDetailService;
        this.driverFilesService = driverFilesService;
    }

    @GetMapping("/produse/{id}")
    public String produsDetalii(@PathVariable Integer id, HttpServletRequest request, Model model) {
        ProductDetail detail = productDetailService.getProductDetail(id,
                        ClientPricingContext.clientId(request), ClientPricingContext.discount(request))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        StocuriSiteView produs = detail.getProdus();
        model.addAttribute("detaliu", detail);
        model.addAttribute("categoryTree", catalogService.getCategoryTree());
        model.addAttribute("selectedCategorie", produs.getIdCategorie());
        model.addAttribute("breadcrumb", catalogService.getBreadcrumb(produs.getIdCategorie()));
        model.addAttribute("fisiereDriver", driverFilesService.listDriverFiles(produs.getCodProducator(), produs.getCod())
                .stream()
                .map(nume -> new DriverFile(nume, driverFilesService.downloadUrl(produs.getCodProducator(), produs.getCod(), nume)))
                .toList());
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

        ProductListingResult result = productFilterService.listProducts(categorie, filter,
                ClientPricingContext.clientId(request), ClientPricingContext.discount(request));

        model.addAttribute("rezultat", result);
        model.addAttribute("filtru", filter);
        model.addAttribute("categoryTree", catalogService.getCategoryTree());
        model.addAttribute("selectedCategorie", categorie);
        model.addAttribute("breadcrumb", categorie != null ? catalogService.getBreadcrumb(categorie) : List.of());
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
