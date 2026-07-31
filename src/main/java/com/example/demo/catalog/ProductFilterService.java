package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * Applies sort/price/supplier/availability/attribute filtering to a
 * category's product list, and computes the sidebar filter options (with
 * counts). Mirrors product_category2.rb's per-category filter state, but
 * computed in Java over the category's (small) product list rather than
 * hand-built dynamic SQL - the old Ruby code itself computes filter counts
 * the same way, in-memory over the loaded product list.
 */
@Service
public class ProductFilterService {

    private static final Integer ANONYMOUS_CLIENT = 0;

    private final StocuriSiteViewRepository stocuriRepository;
    private final AtributeCategorieRepository atributeCategorieRepository;
    private final AtributeCategorieValoareRepository atributeCategorieValoareRepository;
    private final AtributeProdusRepository atributeProdusRepository;

    public ProductFilterService(StocuriSiteViewRepository stocuriRepository,
            AtributeCategorieRepository atributeCategorieRepository,
            AtributeCategorieValoareRepository atributeCategorieValoareRepository,
            AtributeProdusRepository atributeProdusRepository) {
        this.stocuriRepository = stocuriRepository;
        this.atributeCategorieRepository = atributeCategorieRepository;
        this.atributeCategorieValoareRepository = atributeCategorieValoareRepository;
        this.atributeProdusRepository = atributeProdusRepository;
    }

    public SearchResult search(String query) {
        List<StocuriSiteView> produse = stocuriRepository.searchByNameOrCode(ANONYMOUS_CLIENT, query);
        Map<Integer, BigDecimal> pretRonById = produse.stream()
                .collect(Collectors.toMap(StocuriSiteView::getId, StocuriSiteView::getPretRonAnonim));
        return new SearchResult(produse, pretRonById);
    }

    public ProductListingResult listProducts(Integer categorieId, ProductFilter filter) {
        List<StocuriSiteView> allInCategory = (categorieId == null)
                ? stocuriRepository.findByIdclientAndOnlineTrueOrderByOrdineAsc(ANONYMOUS_CLIENT)
                : stocuriRepository.findByIdclientAndOnlineTrueAndIdCategorieOrderByOrdineAsc(ANONYMOUS_CLIENT, categorieId);

        Map<Integer, BigDecimal> pretRonById = allInCategory.stream()
                .collect(Collectors.toMap(StocuriSiteView::getId, StocuriSiteView::getPretRonAnonim));

        List<Integer> ids = allInCategory.stream().map(StocuriSiteView::getId).toList();
        Map<Integer, List<AtributeProdus>> attributesByProduct = ids.isEmpty()
                ? Map.of()
                : atributeProdusRepository.findBySvidIn(ids).stream()
                        .collect(Collectors.groupingBy(AtributeProdus::getSvid));

        List<AttributeFilterGroup> attributeGroups = categorieId == null
                ? List.of()
                : buildAttributeGroups(categorieId, allInCategory, attributesByProduct, filter.getActiveAttributes());

        List<SupplierOption> suppliers = buildSupplierOptions(allInCategory);

        List<StocuriSiteView> filtered = allInCategory.stream()
                .filter(p -> matchesSupplier(p, filter))
                .filter(p -> matchesAvailability(p, filter))
                .filter(p -> matchesName(p, filter))
                .filter(p -> matchesPrice(p, filter, pretRonById))
                .filter(p -> matchesAttributes(p, filter, attributesByProduct))
                .sorted(comparatorFor(filter, pretRonById))
                .toList();

        return new ProductListingResult(filtered, pretRonById, attributeGroups, suppliers, allInCategory.size());
    }

    private List<AttributeFilterGroup> buildAttributeGroups(Integer categorieId, List<StocuriSiteView> allInCategory,
            Map<Integer, List<AtributeProdus>> attributesByProduct, Map<String, Set<String>> activeAttributes) {
        List<AttributeFilterGroup> groups = new ArrayList<>();
        for (AtributeCategorie atribut : atributeCategorieRepository.findByCategorieIdAndOnlineTrueOrderByNume(categorieId)) {
            List<AtributeCategorieValoare> valori = atributeCategorieValoareRepository
                    .findByIdAtributOrderByValoare(atribut.getId().shortValue());
            Set<String> checked = activeAttributes.getOrDefault(atribut.getNume(), Set.of());

            List<AttributeValueOption> options = new ArrayList<>();
            for (AtributeCategorieValoare valoare : valori) {
                int count = 0;
                for (StocuriSiteView product : allInCategory) {
                    boolean has = attributesByProduct.getOrDefault(product.getId(), List.of()).stream()
                            .anyMatch(a -> atribut.getNume().equals(a.getNume()) && valoare.getValoare().equals(a.getValoare1()));
                    if (has) {
                        count++;
                    }
                }
                if (count > 0) {
                    options.add(new AttributeValueOption(valoare.getValoare(), count, checked.contains(valoare.getValoare())));
                }
            }
            if (!options.isEmpty()) {
                groups.add(new AttributeFilterGroup(atribut.getNume(), options));
            }
        }
        return groups;
    }

    private List<SupplierOption> buildSupplierOptions(List<StocuriSiteView> allInCategory) {
        Map<String, Long> counts = allInCategory.stream()
                .filter(p -> p.getCodProducator() != null && !p.getCodProducator().isBlank())
                .collect(Collectors.groupingBy(StocuriSiteView::getCodProducator, Collectors.counting()));
        return counts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new SupplierOption(e.getKey(), e.getValue().intValue()))
                .toList();
    }

    private boolean matchesSupplier(StocuriSiteView p, ProductFilter filter) {
        return filter.getProducatorCod() == null || filter.getProducatorCod().equalsIgnoreCase(p.getCodProducator());
    }

    private boolean matchesAvailability(StocuriSiteView p, ProductFilter filter) {
        return !filter.isDoarDisponibile() || p.isDisponibil();
    }

    private boolean matchesName(StocuriSiteView p, ProductFilter filter) {
        if (filter.getNameFilter() == null || filter.getNameFilter().isBlank()) {
            return true;
        }
        String name = p.getNumeAfisat();
        return name != null && name.toLowerCase().contains(filter.getNameFilter().toLowerCase());
    }

    private boolean matchesPrice(StocuriSiteView p, ProductFilter filter, Map<Integer, BigDecimal> pretRonById) {
        BigDecimal pret = pretRonById.get(p.getId());
        if (filter.getPretMin() != null && pret.compareTo(filter.getPretMin()) < 0) {
            return false;
        }
        if (filter.getPretMax() != null && pret.compareTo(filter.getPretMax()) > 0) {
            return false;
        }
        return true;
    }

    private boolean matchesAttributes(StocuriSiteView p, ProductFilter filter, Map<Integer, List<AtributeProdus>> attributesByProduct) {
        if (filter.getActiveAttributes().isEmpty()) {
            return true;
        }
        List<AtributeProdus> productAttributes = attributesByProduct.getOrDefault(p.getId(), List.of());
        for (Map.Entry<String, Set<String>> active : filter.getActiveAttributes().entrySet()) {
            if (active.getValue().isEmpty()) {
                continue;
            }
            boolean matchesThisAttribute = productAttributes.stream()
                    .anyMatch(a -> active.getKey().equals(a.getNume()) && active.getValue().contains(a.getValoare1()));
            if (!matchesThisAttribute) {
                return false;
            }
        }
        return true;
    }

    private Comparator<StocuriSiteView> comparatorFor(ProductFilter filter, Map<Integer, BigDecimal> pretRonById) {
        Comparator<StocuriSiteView> byOrdine = Comparator.comparing(p -> p.getOrdine() == null ? 0 : p.getOrdine());
        Comparator<StocuriSiteView> byPrice = Comparator.comparing(p -> pretRonById.get(p.getId()));
        return switch (filter.getSort()) {
            case "price_asc" -> byPrice;
            case "price_desc" -> byPrice.reversed();
            default -> byOrdine;
        };
    }
}
