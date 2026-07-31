package com.example.demo.cart;

import java.math.BigDecimal;

/** Result of placing an order - the real comenzi_web row has no "total" column, so this carries it alongside the id for the confirmation page. */
public class ComandaPlasata {

    private final Integer comandaId;
    private final BigDecimal total;

    public ComandaPlasata(Integer comandaId, BigDecimal total) {
        this.comandaId = comandaId;
        this.total = total;
    }

    public Integer getComandaId() {
        return comandaId;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
