package com.example.demo.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "atribute_categorii")
public class AtributeCategorie {

    @Id
    private Integer id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "categorie_id")
    private Integer categorieId;

    @Column(name = "online")
    private Boolean online;

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public Boolean getOnline() {
        return online;
    }
}
