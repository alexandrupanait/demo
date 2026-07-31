package com.example.demo.catalog;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Read-only mapping of the extra {@code produse} columns the storefront
 * detail page needs but {@code stocuri_site_view} doesn't carry.
 */
@Entity
@Immutable
@Table(name = "produse")
public class ProdusDetalii {

    @Id
    private Integer id;

    @Column(name = "nume_en")
    private String numeEn;

    @Column(name = "spec")
    private String spec;

    @Column(name = "spec_en")
    private String specEn;

    @Column(name = "pdf")
    private String pdf;

    @Column(name = "omologare")
    private String omologare;

    public Integer getId() {
        return id;
    }

    public String getNumeEn() {
        return numeEn;
    }

    public String getSpec() {
        return spec;
    }

    public String getSpecEn() {
        return specEn;
    }

    public String getPdf() {
        return pdf;
    }

    public String getOmologare() {
        return omologare;
    }

    public boolean isHasPdf() {
        return pdf != null && !pdf.isBlank() && !pdf.equals("http://www.ral.ro/template/pdfprod/");
    }
}
