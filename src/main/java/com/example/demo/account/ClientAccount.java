package com.example.demo.account;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Mapping of {@code clienti} - the client company a {@link ClientUser}
 * belongs to. Read everywhere in the ralonline portal; also written once,
 * by registration - real writes to the real table (confirmed with the
 * user), since a new account has to work with the existing login flow that
 * reads from this same table.
 */
@Entity
@Table(name = "clienti")
public class ClientAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clienti_id_seq")
    @SequenceGenerator(name = "clienti_id_seq", sequenceName = "clienti_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "firma")
    private String firma;

    @Column(name = "codfiscal")
    private String codFiscal;

    @Column(name = "procent")
    private BigDecimal procent;

    @Column(name = "activitate")
    private String activitate;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "sediu_localitate")
    private String sediuLocalitate;

    @Column(name = "sediu_strada")
    private String sediuStrada;

    @Column(name = "p_type")
    private String pType;

    @Column(name = "status")
    private String status;

    @Column(name = "sursa")
    private String sursa;

    public Integer getId() {
        return id;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getCodFiscal() {
        return codFiscal;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }

    public BigDecimal getProcent() {
        return procent;
    }

    public void setProcent(BigDecimal procent) {
        this.procent = procent;
    }

    public String getActivitate() {
        return activitate;
    }

    public void setActivitate(String activitate) {
        this.activitate = activitate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSediuLocalitate() {
        return sediuLocalitate;
    }

    public void setSediuLocalitate(String sediuLocalitate) {
        this.sediuLocalitate = sediuLocalitate;
    }

    public String getSediuStrada() {
        return sediuStrada;
    }

    public void setSediuStrada(String sediuStrada) {
        this.sediuStrada = sediuStrada;
    }

    public String getPType() {
        return pType;
    }

    public void setPType(String pType) {
        this.pType = pType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSursa() {
        return sursa;
    }

    public void setSursa(String sursa) {
        this.sursa = sursa;
    }
}
