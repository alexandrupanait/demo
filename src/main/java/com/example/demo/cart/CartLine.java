package com.example.demo.cart;

import java.math.BigDecimal;

import com.example.demo.catalog.StocuriSiteView;

public class CartLine {

    private static final BigDecimal VAT_MULTIPLIER = new BigDecimal("1.21");

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

    // pretUnitar/subtotal stay fara TVA (that's what LegacyOrderService needs
    // for the real order tables) - these are only for what the customer sees.
    public BigDecimal getPretUnitarCuTva() {
        return pretUnitar.multiply(VAT_MULTIPLIER).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public BigDecimal getSubtotalCuTva() {
        return subtotal.multiply(VAT_MULTIPLIER).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    // The real cart page shows "Valoare fara TVA" and "Valoare TVA" as separate
    // columns (not a combined cu-TVA figure) - these are just the TVA delta.
    public BigDecimal getTvaUnitar() {
        return getPretUnitarCuTva().subtract(pretUnitar);
    }

    public BigDecimal getTvaSubtotal() {
        return getSubtotalCuTva().subtract(subtotal);
    }

    public boolean isInStoc() {
        BigDecimal stoc = produs.getCantitate();
        return stoc != null && stoc.compareTo(BigDecimal.valueOf(cantitate)) >= 0;
    }
}
