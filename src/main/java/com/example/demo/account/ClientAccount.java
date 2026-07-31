package com.example.demo.account;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Read-only mapping of {@code clienti} - the client company a {@link ClientUser} belongs to. */
@Entity
@Immutable
@Table(name = "clienti")
public class ClientAccount {

    @Id
    private Integer id;

    @Column(name = "firma")
    private String firma;

    @Column(name = "procent")
    private BigDecimal procent;

    @Column(name = "activitate")
    private String activitate;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    public Integer getId() {
        return id;
    }

    public String getFirma() {
        return firma;
    }

    public BigDecimal getProcent() {
        return procent;
    }

    public String getActivitate() {
        return activitate;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }
}
