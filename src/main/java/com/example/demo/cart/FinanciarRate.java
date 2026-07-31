package com.example.demo.cart;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Read-only lookup of named financial values ({@code financiar}) - e.g. COPUSD/COPEUR exchange rates. */
@Entity
@Immutable
@Table(name = "financiar")
public class FinanciarRate {

    @Id
    private Integer id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "valoare")
    private BigDecimal valoare;

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public BigDecimal getValoare() {
        return valoare;
    }
}
