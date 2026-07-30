package com.example.demo.catalog;

import java.math.BigDecimal;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produse")
public class Produs {

    @Id
    private Integer id;

    @Column(name = "cod")
    private String cod;

    @Column(name = "nume")
    private String nume;

    @Column(name = "nume_en")
    private String numeEn;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "discontinued")
    private Boolean discontinued;

    @Column(name = "url_poza")
    private String urlPoza;

    @Column(name = "url_thumbnail")
    private String urlThumbnail;

    @Column(name = "greutate")
    private BigDecimal greutate;

    @Column(name = "cod_producator")
    private String codProducator;

    @Column(name = "ordine")
    private Integer ordine;

    // idcategorie is not always a real category id in the legacy data (e.g. 0),
    // so a missing row must resolve to null instead of throwing.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcategorie", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private CategorieProdus categorie;

    public Integer getId() {
        return id;
    }

    public String getCod() {
        return cod;
    }

    public String getNume() {
        return nume;
    }

    public String getNumeEn() {
        return numeEn;
    }

    public Boolean getOnline() {
        return online;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public String getUrlPoza() {
        return urlPoza;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public BigDecimal getGreutate() {
        return greutate;
    }

    public String getCodProducator() {
        return codProducator;
    }

    public Integer getOrdine() {
        return ordine;
    }

    public CategorieProdus getCategorie() {
        return categorie;
    }
}
