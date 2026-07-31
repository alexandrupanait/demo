package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SearchResult {

    private final List<StocuriSiteView> produse;
    private final Map<Integer, BigDecimal> pretRonById;

    public SearchResult(List<StocuriSiteView> produse, Map<Integer, BigDecimal> pretRonById) {
        this.produse = produse;
        this.pretRonById = pretRonById;
    }

    public List<StocuriSiteView> getProduse() {
        return produse;
    }

    public BigDecimal pretPentru(Integer id) {
        return pretRonById.get(id);
    }
}
