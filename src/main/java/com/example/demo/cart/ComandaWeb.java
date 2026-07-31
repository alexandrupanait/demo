package com.example.demo.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/** The real order row ({@code comenzi_web}), mirroring legacy LegacyComandaWeb#init. */
@Entity
@Table(name = "comenzi_web")
public class ComandaWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comenzi_web_id_seq")
    @SequenceGenerator(name = "comenzi_web_id_seq", sequenceName = "comenzi_web_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "idclient")
    private Integer idClient;

    @Column(name = "firma")
    private String firma;

    @Column(name = "cf")
    private String cf;

    @Column(name = "localitatea")
    private String localitatea;

    @Column(name = "persoana")
    private String persoana;

    @Column(name = "costtransport")
    private BigDecimal costTransport;

    @Column(name = "transport")
    private String transport;

    @Column(name = "curs")
    private BigDecimal curs;

    @Column(name = "plata")
    private String plata;

    @Column(name = "stare")
    private String stare;

    @Column(name = "idautor")
    private Integer idAutor;

    @Column(name = "curseur")
    private BigDecimal cursEur;

    @Column(name = "info")
    private String info;

    @Column(name = "info_livrare")
    private String infoLivrare;

    @Column(name = "email_confirmare")
    private String emailConfirmare;

    @Column(name = "data")
    private LocalDateTime data;

    public Integer getId() {
        return id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getLocalitatea() {
        return localitatea;
    }

    public void setLocalitatea(String localitatea) {
        this.localitatea = localitatea;
    }

    public String getPersoana() {
        return persoana;
    }

    public void setPersoana(String persoana) {
        this.persoana = persoana;
    }

    public BigDecimal getCostTransport() {
        return costTransport;
    }

    public void setCostTransport(BigDecimal costTransport) {
        this.costTransport = costTransport;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public BigDecimal getCurs() {
        return curs;
    }

    public void setCurs(BigDecimal curs) {
        this.curs = curs;
    }

    public String getPlata() {
        return plata;
    }

    public void setPlata(String plata) {
        this.plata = plata;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public BigDecimal getCursEur() {
        return cursEur;
    }

    public void setCursEur(BigDecimal cursEur) {
        this.cursEur = cursEur;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoLivrare() {
        return infoLivrare;
    }

    public void setInfoLivrare(String infoLivrare) {
        this.infoLivrare = infoLivrare;
    }

    public String getEmailConfirmare() {
        return emailConfirmare;
    }

    public void setEmailConfirmare(String emailConfirmare) {
        this.emailConfirmare = emailConfirmare;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
