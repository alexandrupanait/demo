package com.example.demo.cart;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/** Order line item ({@code continut_comenzi_web}). */
@Entity
@Table(name = "continut_comenzi_web")
public class ContinutComandaWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "continut_comenzi_web_id_seq")
    @SequenceGenerator(name = "continut_comenzi_web_id_seq", sequenceName = "continut_comenzi_web_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "comid")
    private Integer comId;

    @Column(name = "svid")
    private Integer svid;

    @Column(name = "produs")
    private String produs;

    @Column(name = "cant")
    private Integer cant;

    @Column(name = "pu")
    private BigDecimal pu;

    @Column(name = "val")
    private BigDecimal val;

    @Column(name = "tva")
    private BigDecimal tva;

    @Column(name = "idvaluta")
    private Integer idValuta;

    public Integer getId() {
        return id;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getSvid() {
        return svid;
    }

    public void setSvid(Integer svid) {
        this.svid = svid;
    }

    public String getProdus() {
        return produs;
    }

    public void setProdus(String produs) {
        this.produs = produs;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    public BigDecimal getPu() {
        return pu;
    }

    public void setPu(BigDecimal pu) {
        this.pu = pu;
    }

    public BigDecimal getVal() {
        return val;
    }

    public void setVal(BigDecimal val) {
        this.val = val;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public Integer getIdValuta() {
        return idValuta;
    }

    public void setIdValuta(Integer idValuta) {
        this.idValuta = idValuta;
    }
}
