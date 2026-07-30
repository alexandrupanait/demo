package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductListingResult {

    private final List<StocuriSiteView> produse;
    private final Map<Integer, BigDecimal> pretRonById;
    private final List<AttributeFilterGroup> attributeGroups;
    private final List<SupplierOption> suppliers;
    private final int totalInainteDeFiltre;

    public ProductListingResult(List<StocuriSiteView> produse, Map<Integer, BigDecimal> pretRonById,
            List<AttributeFilterGroup> attributeGroups, List<SupplierOption> suppliers, int totalInainteDeFiltre) {
        this.produse = produse;
        this.pretRonById = pretRonById;
        this.attributeGroups = attributeGroups;
        this.suppliers = suppliers;
        this.totalInainteDeFiltre = totalInainteDeFiltre;
    }

    public List<StocuriSiteView> getProduse() {
        return produse;
    }

    public BigDecimal pretPentru(Integer id) {
        return pretRonById.get(id);
    }

    public List<AttributeFilterGroup> getAttributeGroups() {
        return attributeGroups;
    }

    public List<SupplierOption> getSuppliers() {
        return suppliers;
    }

    public int getTotalInainteDeFiltre() {
        return totalInainteDeFiltre;
    }
}
