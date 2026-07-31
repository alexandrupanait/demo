package com.example.demo.cart;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/** Per-invoice exchange rate row ({@code cursuri_facturi_web}), one per currency, mirroring the legacy Invoice#reset_exchange_rates. */
@Entity
@Table(name = "cursuri_facturi_web")
public class CursFacturaWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cursuri_facturi_web_id_seq")
    @SequenceGenerator(name = "cursuri_facturi_web_id_seq", sequenceName = "cursuri_facturi_web_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "numar_factura")
    private Integer numarFactura;

    @Column(name = "idvaluta")
    private Integer idValuta;

    @Column(name = "curs")
    private BigDecimal curs;

    public CursFacturaWeb() {
    }

    public CursFacturaWeb(Integer numarFactura, Integer idValuta, BigDecimal curs) {
        this.numarFactura = numarFactura;
        this.idValuta = idValuta;
        this.curs = curs;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumarFactura() {
        return numarFactura;
    }

    public void setNumarFactura(Integer numarFactura) {
        this.numarFactura = numarFactura;
    }

    public Integer getIdValuta() {
        return idValuta;
    }

    public void setIdValuta(Integer idValuta) {
        this.idValuta = idValuta;
    }

    public BigDecimal getCurs() {
        return curs;
    }

    public void setCurs(BigDecimal curs) {
        this.curs = curs;
    }
}
