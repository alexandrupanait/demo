package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/** Request-driven filter/sort state for a category's product listing, mirroring product_category2.rb's per-request params. */
public class ProductFilter {

    private String sort = "rec_ral";
    private BigDecimal pretMin;
    private BigDecimal pretMax;
    private String producatorCod;
    private boolean doarDisponibile;
    private String nameFilter;
    private Map<String, Set<String>> activeAttributes = Map.of();

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        if (sort != null && !sort.isBlank()) {
            this.sort = sort;
        }
    }

    public BigDecimal getPretMin() {
        return pretMin;
    }

    public void setPretMin(BigDecimal pretMin) {
        this.pretMin = pretMin;
    }

    public BigDecimal getPretMax() {
        return pretMax;
    }

    public void setPretMax(BigDecimal pretMax) {
        this.pretMax = pretMax;
    }

    public String getProducatorCod() {
        return producatorCod;
    }

    public void setProducatorCod(String producatorCod) {
        this.producatorCod = producatorCod;
    }

    public boolean isDoarDisponibile() {
        return doarDisponibile;
    }

    public void setDoarDisponibile(boolean doarDisponibile) {
        this.doarDisponibile = doarDisponibile;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public Map<String, Set<String>> getActiveAttributes() {
        return activeAttributes;
    }

    public void setActiveAttributes(Map<String, Set<String>> activeAttributes) {
        this.activeAttributes = activeAttributes == null ? Map.of() : activeAttributes;
    }
}
