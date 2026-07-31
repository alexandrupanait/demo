package com.example.demo.account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Read-only mapping of jurnalvanzari_web - the real sales journal, used here as the invoice list. */
@Entity
@Immutable
@Table(name = "jurnalvanzari_web")
public class Invoice {

    @Id
    private Integer numar;

    @Column(name = "idclient")
    private Integer idClient;

    @Column(name = "data")
    private OffsetDateTime data;

    @Column(name = "cumparator")
    private String cumparator;

    @Column(name = "codfiscal")
    private String codFiscal;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "tva")
    private BigDecimal tva;

    @Column(name = "baza")
    private BigDecimal baza;

    @Column(name = "transport")
    private String transport;

    @Column(name = "modplata")
    private String modPlata;

    public Integer getNumar() {
        return numar;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public OffsetDateTime getData() {
        return data;
    }

    public String getCumparator() {
        return cumparator;
    }

    public String getCodFiscal() {
        return codFiscal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public BigDecimal getBaza() {
        return baza;
    }

    public String getTransport() {
        return transport;
    }

    public String getModPlata() {
        return modPlata;
    }
}
