package com.example.demo.catalog;

import java.util.List;

public class AttributeFilterGroup {

    private final String nume;
    private final List<AttributeValueOption> valori;

    public AttributeFilterGroup(String nume, List<AttributeValueOption> valori) {
        this.nume = nume;
        this.valori = valori;
    }

    public String getNume() {
        return nume;
    }

    public List<AttributeValueOption> getValori() {
        return valori;
    }
}
