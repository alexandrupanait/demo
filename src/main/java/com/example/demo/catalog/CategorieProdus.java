package com.example.demo.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorii_produse2")
public class CategorieProdus {

    @Id
    private Integer id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "descriere")
    private String descriere;

    @Column(name = "id_parinte")
    private Integer idParinte;

    @Column(name = "nr_ordine")
    private Integer nrOrdine;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "activ")
    private Boolean activ;

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public Integer getIdParinte() {
        return idParinte;
    }

    public Integer getNrOrdine() {
        return nrOrdine;
    }

    public Boolean getOnline() {
        return online;
    }

    public Boolean getActiv() {
        return activ;
    }
}
