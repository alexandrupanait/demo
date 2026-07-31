package com.example.demo.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "solutii")
public class Solutie {

    @Id
    private Short id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "descriere")
    private String descriere;

    @Column(name = "cod_producator")
    private String codProducator;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "ordine")
    private Short ordine;

    public Short getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getCodProducator() {
        return codProducator;
    }

    public Boolean getOnline() {
        return online;
    }

    public Short getOrdine() {
        return ordine;
    }
}
