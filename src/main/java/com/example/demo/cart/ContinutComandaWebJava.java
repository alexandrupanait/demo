package com.example.demo.cart;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "continut_comenzi_web_java")
public class ContinutComandaWebJava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comanda_id")
    private Integer comandaId;

    @Column(name = "produs_id")
    private Integer produsId;

    @Column(name = "produs_cod")
    private String produsCod;

    @Column(name = "produs_nume")
    private String produsNume;

    @Column(name = "cantitate")
    private Integer cantitate;

    @Column(name = "pret_unitar")
    private BigDecimal pretUnitar;

    @Column(name = "valoare")
    private BigDecimal valoare;

    public Integer getId() {
        return id;
    }

    public Integer getComandaId() {
        return comandaId;
    }

    public void setComandaId(Integer comandaId) {
        this.comandaId = comandaId;
    }

    public Integer getProdusId() {
        return produsId;
    }

    public void setProdusId(Integer produsId) {
        this.produsId = produsId;
    }

    public String getProdusCod() {
        return produsCod;
    }

    public void setProdusCod(String produsCod) {
        this.produsCod = produsCod;
    }

    public String getProdusNume() {
        return produsNume;
    }

    public void setProdusNume(String produsNume) {
        this.produsNume = produsNume;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public BigDecimal getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(BigDecimal pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    public BigDecimal getValoare() {
        return valoare;
    }

    public void setValoare(BigDecimal valoare) {
        this.valoare = valoare;
    }
}
