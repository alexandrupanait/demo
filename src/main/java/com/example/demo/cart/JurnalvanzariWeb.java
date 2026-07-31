package com.example.demo.cart;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * The real invoice/sales-journal row ({@code jurnalvanzari_web}) - same table
 * the "Facturi" page reads and the legacy Ruby checkout writes to. Written by
 * real order placement (the user chose real-table writes over the isolated
 * {@code comenzi_web_java} tables, accepting that this syncs into Axapta).
 */
@Entity
@Table(name = "jurnalvanzari_web")
public class JurnalvanzariWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jurnalvanzari_web_numar_seq")
    @SequenceGenerator(name = "jurnalvanzari_web_numar_seq", sequenceName = "jurnalvanzari_web_numar_seq", allocationSize = 1)
    @Column(name = "numar")
    private Integer numar;

    @Column(name = "data")
    private OffsetDateTime data;

    @Column(name = "idclient")
    private Integer idClient;

    @Column(name = "cumparator")
    private String cumparator;

    @Column(name = "codfiscal")
    private String codFiscal;

    @Column(name = "localitate")
    private String localitate;

    @Column(name = "modplata")
    private String modPlata;

    @Column(name = "curs")
    private BigDecimal curs;

    @Column(name = "tva")
    private BigDecimal tva;

    @Column(name = "baza")
    private BigDecimal baza;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "transport")
    private String transport;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "idautor")
    private Integer idAutor;

    @Column(name = "ramburs")
    private Boolean ramburs;

    @Column(name = "ziletp")
    private Integer zileTp;

    @Column(name = "info_livrare")
    private String infoLivrare;

    @Column(name = "info")
    private String info;

    @Column(name = "email_confirmare")
    private String emailConfirmare;

    public Integer getNumar() {
        return numar;
    }

    public OffsetDateTime getData() {
        return data;
    }

    public void setData(OffsetDateTime data) {
        this.data = data;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getCumparator() {
        return cumparator;
    }

    public void setCumparator(String cumparator) {
        this.cumparator = cumparator;
    }

    public String getCodFiscal() {
        return codFiscal;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getModPlata() {
        return modPlata;
    }

    public void setModPlata(String modPlata) {
        this.modPlata = modPlata;
    }

    public BigDecimal getCurs() {
        return curs;
    }

    public void setCurs(BigDecimal curs) {
        this.curs = curs;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getBaza() {
        return baza;
    }

    public void setBaza(BigDecimal baza) {
        this.baza = baza;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public Boolean getRamburs() {
        return ramburs;
    }

    public void setRamburs(Boolean ramburs) {
        this.ramburs = ramburs;
    }

    public Integer getZileTp() {
        return zileTp;
    }

    public void setZileTp(Integer zileTp) {
        this.zileTp = zileTp;
    }

    public String getInfoLivrare() {
        return infoLivrare;
    }

    public void setInfoLivrare(String infoLivrare) {
        this.infoLivrare = infoLivrare;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getEmailConfirmare() {
        return emailConfirmare;
    }

    public void setEmailConfirmare(String emailConfirmare) {
        this.emailConfirmare = emailConfirmare;
    }
}
