package com.example.demo.catalog;

public class SupplierOption {

    private final String cod;
    private final int count;

    public SupplierOption(String cod, int count) {
        this.cod = cod;
        this.count = count;
    }

    public String getCod() {
        return cod;
    }

    public int getCount() {
        return count;
    }
}
