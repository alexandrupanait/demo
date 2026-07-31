package com.example.demo.catalog;

/** One downloadable driver/firmware file for the product detail page. */
public class DriverFile {

    private final String nume;
    private final String url;

    public DriverFile(String nume, String url) {
        this.nume = nume;
        this.url = url;
    }

    public String getNume() {
        return nume;
    }

    public String getUrl() {
        return url;
    }
}
