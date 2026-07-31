package com.example.demo.cart;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/** Invoice line item ({@code continutfacturi_web}), mirroring legacy InvoiceProduct#before_insert_legacy_adapter. */
@Entity
@Table(name = "continutfacturi_web")
public class ContinutFacturaWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "continutfacturi_web_id_seq")
    @SequenceGenerator(name = "continutfacturi_web_id_seq", sequenceName = "continutfacturi_web_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "factnr")
    private Integer factNr;

    @Column(name = "produs")
    private String produs;

    @Column(name = "svid")
    private Integer svid;

    @Column(name = "pu")
    private BigDecimal pu;

    @Column(name = "valoaretva")
    private BigDecimal valoareTva;

    @Column(name = "valoare")
    private BigDecimal valoare;

    @Column(name = "garantie")
    private Integer garantie;

    @Column(name = "idvaluta")
    private Integer idValuta;

    @Column(name = "pondere_procent")
    private BigDecimal ponderePercent;

    @Column(name = "cota_tva")
    private BigDecimal cotaTva;

    @Column(name = "pu_lista")
    private BigDecimal puLista;

    @Column(name = "id_valuta_pret_lista")
    private Integer idValutaPretLista;

    @Column(name = "pu_final")
    private BigDecimal puFinal;

    @Column(name = "curs_pu")
    private BigDecimal cursPu;

    public Integer getId() {
        return id;
    }

    public Integer getFactNr() {
        return factNr;
    }

    public void setFactNr(Integer factNr) {
        this.factNr = factNr;
    }

    public String getProdus() {
        return produs;
    }

    public void setProdus(String produs) {
        this.produs = produs;
    }

    public Integer getSvid() {
        return svid;
    }

    public void setSvid(Integer svid) {
        this.svid = svid;
    }

    public BigDecimal getPu() {
        return pu;
    }

    public void setPu(BigDecimal pu) {
        this.pu = pu;
    }

    public BigDecimal getValoareTva() {
        return valoareTva;
    }

    public void setValoareTva(BigDecimal valoareTva) {
        this.valoareTva = valoareTva;
    }

    public BigDecimal getValoare() {
        return valoare;
    }

    public void setValoare(BigDecimal valoare) {
        this.valoare = valoare;
    }

    public Integer getGarantie() {
        return garantie;
    }

    public void setGarantie(Integer garantie) {
        this.garantie = garantie;
    }

    public Integer getIdValuta() {
        return idValuta;
    }

    public void setIdValuta(Integer idValuta) {
        this.idValuta = idValuta;
    }

    public BigDecimal getPonderePercent() {
        return ponderePercent;
    }

    public void setPonderePercent(BigDecimal ponderePercent) {
        this.ponderePercent = ponderePercent;
    }

    public BigDecimal getCotaTva() {
        return cotaTva;
    }

    public void setCotaTva(BigDecimal cotaTva) {
        this.cotaTva = cotaTva;
    }

    public BigDecimal getPuLista() {
        return puLista;
    }

    public void setPuLista(BigDecimal puLista) {
        this.puLista = puLista;
    }

    public Integer getIdValutaPretLista() {
        return idValutaPretLista;
    }

    public void setIdValutaPretLista(Integer idValutaPretLista) {
        this.idValutaPretLista = idValutaPretLista;
    }

    public BigDecimal getPuFinal() {
        return puFinal;
    }

    public void setPuFinal(BigDecimal puFinal) {
        this.puFinal = puFinal;
    }

    public BigDecimal getCursPu() {
        return cursPu;
    }

    public void setCursPu(BigDecimal cursPu) {
        this.cursPu = cursPu;
    }
}
