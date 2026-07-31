package com.example.demo.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "producatori")
public class Producator {

    private static final String PLACEHOLDER_LOGO = "/images/action_stop.gif";

    @Id
    private Integer id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prezentare")
    private String prezentare;

    @Column(name = "site")
    private String site;

    @Column(name = "sigla_mica")
    private String siglaMica;

    @Column(name = "sigla_mare")
    private String siglaMare;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "ordine_web")
    private Short ordineWeb;

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getPrezentare() {
        return prezentare;
    }

    public String getSite() {
        return site;
    }

    public Boolean getOnline() {
        return online;
    }

    public Short getOrdineWeb() {
        return ordineWeb;
    }

    /** Mirrors Ruby's prezentare_for_search - strips HTML tags so a truncated preview never breaks a tag. */
    public String getPrezentareText() {
        return prezentare == null ? "" : prezentare.replaceAll("<[^>]*>", "");
    }

    public String getImage() {
        return relativize(siglaMica);
    }

    public String getImageLarge() {
        return relativize(siglaMare);
    }

    private static String relativize(String url) {
        if (url == null || url.isBlank()) {
            return PLACEHOLDER_LOGO;
        }
        return url.replace("http://www.ral.ro", "");
    }
}
