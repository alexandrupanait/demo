package com.example.demo.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

@Entity
@Table(name = "atribute_produse")
public class AtributeProdus {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "svid")
    private Integer svid;

    @Column(name = "nume")
    private String nume;

    @Column(name = "valoare1")
    private String valoare1;

    public Long getId() {
        return id;
    }

    public Integer getSvid() {
        return svid;
    }

    public String getNume() {
        return nume;
    }

    public String getValoare1() {
        return valoare1;
    }
}
