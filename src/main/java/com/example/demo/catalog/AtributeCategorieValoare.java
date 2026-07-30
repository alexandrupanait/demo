package com.example.demo.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "atribute_categorii_valori")
public class AtributeCategorieValoare {

    @Id
    private Integer id;

    @Column(name = "id_atribut")
    private Short idAtribut;

    @Column(name = "valoare")
    private String valoare;

    public Integer getId() {
        return id;
    }

    public Short getIdAtribut() {
        return idAtribut;
    }

    public String getValoare() {
        return valoare;
    }
}
