package com.example.demo.catalog;

import java.math.BigDecimal;

public class ProductDetail {

    private final StocuriSiteView produs;
    private final BigDecimal pretRon;
    private final ProdusDetalii detalii;

    public ProductDetail(StocuriSiteView produs, BigDecimal pretRon, ProdusDetalii detalii) {
        this.produs = produs;
        this.pretRon = pretRon;
        this.detalii = detalii;
    }

    public StocuriSiteView getProdus() {
        return produs;
    }

    public BigDecimal getPretRon() {
        return pretRon;
    }

    public ProdusDetalii getDetalii() {
        return detalii;
    }
}
