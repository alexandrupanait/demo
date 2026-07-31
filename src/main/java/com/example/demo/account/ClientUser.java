package com.example.demo.account;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Read-only mapping of {@code personal_clienti} - a login/contact person
 * belonging to a client company ({@link ClientAccount}). Password is stored
 * in plaintext in this data (confirmed in the old Ruby source); we only
 * ever read it for comparison, never write.
 */
@Entity
@Immutable
@Table(name = "personal_clienti")
public class ClientUser {

    @Id
    private Integer id;

    @Column(name = "idclient")
    private Integer idClient;

    @Column(name = "utilizator")
    private String utilizator;

    @Column(name = "parola")
    private String parola;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "activ")
    private Boolean activ;

    @Column(name = "admin")
    private Boolean admin;

    public Integer getId() {
        return id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public String getUtilizator() {
        return utilizator;
    }

    public String getParola() {
        return parola;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Boolean getActiv() {
        return activ;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String getNumeComplet() {
        return (prenume == null ? "" : prenume + " ") + (nume == null ? "" : nume);
    }
}
