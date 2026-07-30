package com.example.demo.catalog;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Read-only mapping of the {@code stocuri_site_view} database view (already
 * used by the old site for storefront browsing - joins produse/stocuri/
 * preturi/valute). The view has one row per product per client price
 * context (idclient), so the two together form the real key; Phase 1 only
 * ever queries idclient = 0 (the anonymous/reference price row).
 */
@Entity
@Immutable
@Table(name = "stocuri_site_view")
@IdClass(StocuriSiteViewId.class)
public class StocuriSiteView {

    @Id
    @Column(name = "id")
    private Integer id;

    @Id
    @Column(name = "idclient")
    private Integer idclient;

    @Column(name = "cod")
    private String cod;

    @Column(name = "discontinued")
    private Boolean discontinued;

    @Column(name = "nume_afisare_invers")
    private String numeAfisareInvers;

    @Column(name = "cod_producator")
    private String codProducator;

    @Column(name = "ordine")
    private Integer ordine;

    @Column(name = "idcategorie")
    private Integer idCategorie;

    @Column(name = "garantie")
    private Integer garantie;

    @Column(name = "stoc_p_limita")
    private Integer stocPLimita;

    @Column(name = "info_web")
    private String infoWeb;

    @Column(name = "url_poza")
    private String urlPoza;

    @Column(name = "url_thumbnail")
    private String urlThumbnail;

    @Column(name = "disponibil_la_comanda")
    private Boolean disponibilLaComanda;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "cantitate")
    private BigDecimal cantitate;

    @Column(name = "idvaluta")
    private Integer idValuta;

    @Column(name = "pret")
    private BigDecimal pret;

    @Column(name = "idvaluta_pret_referinta")
    private Integer idValutaPretReferinta;

    @Column(name = "pret_referinta")
    private BigDecimal pretReferinta;

    @Column(name = "discount_gv")
    private BigDecimal discountGv;

    @Column(name = "curs_usd")
    private BigDecimal cursUsd;

    @Column(name = "curs_eur")
    private BigDecimal cursEur;

    @Column(name = "eta")
    private LocalDateTime eta;

    @Column(name = "categorie_vr")
    private String categorieVr;

    @Column(name = "pret_fix")
    private Boolean pretFix;

    public Integer getId() {
        return id;
    }

    public Integer getIdclient() {
        return idclient;
    }

    public String getCod() {
        return cod;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public String getNumeAfisareInvers() {
        return numeAfisareInvers;
    }

    /** Mirrors Ruby's {@code nume_produs(lang).split('|')[0]} - the plain product name without the "| manufacturer | code" suffix. */
    public String getNumeAfisat() {
        if (numeAfisareInvers == null) {
            return null;
        }
        return numeAfisareInvers.split("\\|")[0].trim();
    }

    public String getCodProducator() {
        return codProducator;
    }

    public Integer getOrdine() {
        return ordine;
    }

    public Integer getIdCategorie() {
        return idCategorie;
    }

    public Integer getGarantie() {
        return garantie;
    }

    public Integer getStocPLimita() {
        return stocPLimita;
    }

    public String getInfoWeb() {
        return infoWeb;
    }

    public String getUrlPoza() {
        return urlPoza;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public Boolean getDisponibilLaComanda() {
        return disponibilLaComanda;
    }

    public Boolean getOnline() {
        return online;
    }

    public BigDecimal getCantitate() {
        return cantitate;
    }

    public Integer getIdValuta() {
        return idValuta;
    }

    public BigDecimal getPret() {
        return pret;
    }

    public Integer getIdValutaPretReferinta() {
        return idValutaPretReferinta;
    }

    public BigDecimal getPretReferinta() {
        return pretReferinta;
    }

    public BigDecimal getDiscountGv() {
        return discountGv;
    }

    public BigDecimal getCursUsd() {
        return cursUsd;
    }

    public BigDecimal getCursEur() {
        return cursEur;
    }

    public LocalDateTime getEta() {
        return eta;
    }

    public String getCategorieVr() {
        return categorieVr;
    }

    public Boolean getPretFix() {
        return pretFix;
    }

    private static final BigDecimal ANONYMOUS_MARKUP = new BigDecimal("1.20");

    /**
     * Mirrors the anonymous/no-client branch of Ruby's {@code pret_ron_notva}:
     * reference price, converted to RON, with either the clearance discount
     * ("lichidare" categories) or the default +20% markup applied. Per-client
     * negotiated pricing and the one hardcoded special-case product id from
     * the Ruby code are out of scope until client login exists (Phase 3).
     */
    public BigDecimal getPretRonAnonim() {
        if (pretReferinta == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal price;
        if (Boolean.TRUE.equals(pretFix)) {
            price = pretReferinta;
        } else if ("lichidare".equals(categorieVr)) {
            BigDecimal discount = discountGv == null ? BigDecimal.ZERO : discountGv;
            price = pretReferinta.subtract(pretReferinta.multiply(discount).divide(new BigDecimal("100")));
        } else {
            price = pretReferinta.multiply(ANONYMOUS_MARKUP);
        }
        BigDecimal rate = switch (idValutaPretReferinta == null ? 1 : idValutaPretReferinta) {
            case 2 -> cursUsd == null ? BigDecimal.ONE : cursUsd;
            case 3 -> cursEur == null ? BigDecimal.ONE : cursEur;
            default -> BigDecimal.ONE;
        };
        return price.multiply(rate).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    /** Mirrors Ruby's {@code thumb} - strips the old absolute domain and falls back to a placeholder. */
    public String getThumb() {
        return relativize(urlThumbnail);
    }

    /** Mirrors Ruby's {@code poza}. */
    public String getPoza() {
        return relativize(urlPoza);
    }

    private static String relativize(String url) {
        if (url == null || url.isEmpty()) {
            return "/images/image_na.jpg";
        }
        return url.replace("http://www.ral.ro", "");
    }

    public boolean isDisponibil() {
        return cantitate != null && cantitate.signum() > 0;
    }
}
