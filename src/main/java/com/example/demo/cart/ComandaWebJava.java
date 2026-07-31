package com.example.demo.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A real order placed through the Java site - deliberately in a table of
 * its own (not jurnalvanzari_web/comenzi_web, the legacy tables the old
 * site writes to and our "Facturi" page reads from), so test orders never
 * mix with real historical data. See the Phase 4 plan for why.
 */
@Entity
@Table(name = "comenzi_web_java")
public class ComandaWebJava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idclient")
    private Integer idClient;

    @Column(name = "nume_client")
    private String numeClient;

    @Column(name = "email_client")
    private String emailClient;

    @Column(name = "telefon_client")
    private String telefonClient;

    @Column(name = "adresa_livrare")
    private String adresaLivrare;

    @Column(name = "transport")
    private String transport;

    @Column(name = "mod_plata")
    private String modPlata;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "creat_la")
    private LocalDateTime creatLa;

    public Integer getId() {
        return id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getTelefonClient() {
        return telefonClient;
    }

    public void setTelefonClient(String telefonClient) {
        this.telefonClient = telefonClient;
    }

    public String getAdresaLivrare() {
        return adresaLivrare;
    }

    public void setAdresaLivrare(String adresaLivrare) {
        this.adresaLivrare = adresaLivrare;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getModPlata() {
        return modPlata;
    }

    public void setModPlata(String modPlata) {
        this.modPlata = modPlata;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreatLa() {
        return creatLa;
    }

    public void setCreatLa(LocalDateTime creatLa) {
        this.creatLa = creatLa;
    }
}
