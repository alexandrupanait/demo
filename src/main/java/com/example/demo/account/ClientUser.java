package com.example.demo.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Mapping of {@code personal_clienti} - a login/contact person belonging to
 * a client company ({@link ClientAccount}). Password is stored in plaintext
 * in this data (confirmed in the old Ruby source) - we read it directly for
 * comparison at login and write it directly at registration, no hashing
 * (a deliberate, disclosed simplification - see the Phase 3 plan).
 */
@Entity
@Table(name = "personal_clienti")
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_firme_id_seq")
    @SequenceGenerator(name = "personal_firme_id_seq", sequenceName = "personal_firme_id_seq", allocationSize = 1)
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

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

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

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActiv() {
        return activ;
    }

    public void setActiv(Boolean activ) {
        this.activ = activ;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getNumeComplet() {
        return (prenume == null ? "" : prenume + " ") + (nume == null ? "" : nume);
    }
}
