package com.example.demo.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contents")
public class ContentBlock {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "content_ro")
    private String contentRo;

    @Column(name = "listorder")
    private Short listorder;

    @Column(name = "friendly_name_ro")
    private String friendlyNameRo;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public String getContentRo() {
        return contentRo;
    }

    public Short getListorder() {
        return listorder;
    }

    public String getFriendlyNameRo() {
        return friendlyNameRo;
    }
}
