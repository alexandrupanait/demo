package com.example.demo.content;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class NewsItem {

    @Id
    private Long id;

    @Column(name = "title_ro")
    private String titleRo;

    @Column(name = "short_description_ro")
    private String shortDescriptionRo;

    @Column(name = "description_ro")
    private String descriptionRo;

    @Column(name = "date_display")
    private LocalDateTime dateDisplay;

    @Column(name = "active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public String getTitleRo() {
        return titleRo;
    }

    public String getShortDescriptionRo() {
        return shortDescriptionRo;
    }

    public String getDescriptionRo() {
        return descriptionRo;
    }

    public LocalDateTime getDateDisplay() {
        return dateDisplay;
    }

    public Boolean getActive() {
        return active;
    }
}
