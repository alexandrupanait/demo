package com.example.demo.cart;

import java.math.BigDecimal;

import com.example.demo.catalog.StocuriSiteView;

public class CartLine {

    private final StocuriSiteView produs;
    private final int cantitate;
    private final BigDecimal pretUnitar;
    private final BigDecimal subtotal;

    public CartLine(StocuriSiteView produs, int cantitate, BigDecimal pretUnitar, BigDecimal subtotal) {
        this.produs = produs;
        this.cantitate = cantitate;
        this.pretUnitar = pretUnitar;
        this.subtotal = subtotal;
    }

    public StocuriSiteView getProdus() {
        return produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public BigDecimal getPretUnitar() {
        return pretUnitar;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
