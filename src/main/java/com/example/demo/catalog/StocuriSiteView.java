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
 * context (idclient), so the two together form the real key. The
 * idclient=0 row is the anonymous/reference row (always queried, carries
 * the product-level fields); a small, sparse subset of products also have
 * a row for a specific logged-in client's own id, carrying that client's
 * negotiated price - see {@link #getPretRon}.
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

    // Mirrors Ruby's $DISCOUNT_DEFAULT - the markup applied for anonymous
    // (logged-out) visitors. pret_ron_tva always calls pret_ron_notva with
    // this hardcoded value, regardless of caller - not a per-call param in
    // practice. (Earlier Phase 1 code used +20% here; the real value is 15%.)
    private static final BigDecimal ANONYMOUS_DISCOUNT_PERCENT = new BigDecimal("15");

    /**
     * Full port of Ruby's {@code pret_ron_notva}. {@code this} must always be
     * the anonymous/reference row (idclient=0) - it carries pret_referinta,
     * pret_fix, categorie_vr, discount_gv and the exchange rates, which are
     * product-level, not client-specific. {@code clientRow} is the row for
     * the logged-in client's own id, if one exists (most clients don't have
     * product-specific rows - only a general discount percentage). The one
     * hardcoded special-case product id from the Ruby code is still out of
     * scope (a one-off legacy hack, not general pricing logic).
     *
     * <pre>
     * pret_fix              -&gt; pret_referinta, discount 0
     * categorie_vr=lichidare -&gt; pret_referinta, discount -discount_gv
     * a row exists for this exact client -&gt; that row's own pret, discount 0
     * logged in, no client-specific row  -&gt; pret_referinta, discount = client's %
     * anonymous                          -&gt; pret, discount = 15% (ANONYMOUS_DISCOUNT_PERCENT)
     * </pre>
     */
    public BigDecimal getPretRon(StocuriSiteView clientRow, Integer targetIdClient, BigDecimal discountClient) {
        if (pretReferinta == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal price;
        Integer valutaId;
        BigDecimal discount;
        if (Boolean.TRUE.equals(pretFix)) {
            price = pretReferinta;
            valutaId = idValutaPretReferinta;
            discount = BigDecimal.ZERO;
        } else if ("lichidare".equals(categorieVr)) {
            price = pretReferinta;
            valutaId = idValutaPretReferinta;
            discount = (discountGv == null ? BigDecimal.ZERO : discountGv).negate();
        } else if (clientRow != null) {
            price = clientRow.pret;
            valutaId = clientRow.idValuta;
            discount = BigDecimal.ZERO;
        } else if (targetIdClient != null && targetIdClient > 0) {
            price = pretReferinta;
            valutaId = idValutaPretReferinta;
            discount = discountClient == null ? BigDecimal.ZERO : discountClient;
        } else {
            price = pret;
            valutaId = idValuta;
            discount = ANONYMOUS_DISCOUNT_PERCENT;
        }
        BigDecimal withDiscount = price.add(price.multiply(discount).divide(new BigDecimal("100")));
        BigDecimal rate = switch (valutaId == null ? 1 : valutaId) {
            case 2 -> cursUsd == null ? BigDecimal.ONE : cursUsd;
            case 3 -> cursEur == null ? BigDecimal.ONE : cursEur;
            default -> BigDecimal.ONE;
        };
        return withDiscount.multiply(rate).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private static final BigDecimal VAT_MULTIPLIER = new BigDecimal("1.21");

    /**
     * What every customer-facing price on the real site actually shows -
     * Ruby's {@code pret_ron_tva}, which is {@code pret_ron_notva} (see
     * {@link #getPretRon}) with VAT added on top, rounded again. Every
     * template call site in the legacy app uses this wrapper, never the
     * bare notva value - the notva value itself is only meaningful as the
     * "fara TVA" base line-item price already used internally for the real
     * order/invoice tables (LegacyOrderService computes its own TVA from
     * that base separately, matching the real schema).
     */
    public BigDecimal getPretRonCuTva(StocuriSiteView clientRow, Integer targetIdClient, BigDecimal discountClient) {
        BigDecimal faraTva = getPretRon(clientRow, targetIdClient, discountClient);
        return faraTva.multiply(VAT_MULTIPLIER).setScale(2, java.math.RoundingMode.HALF_UP);
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
