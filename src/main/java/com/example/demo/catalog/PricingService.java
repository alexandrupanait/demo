package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Resolves the real price for a product depending on who's looking: an
 * anonymous visitor gets the reference price with the standard markup, a
 * logged-in client gets either their own negotiated per-product row (rare)
 * or the reference price discounted by their account's overall percentage.
 * See StocuriSiteView#getPretRon/#getPretRonCuTva for the actual formulas
 * (ported from Ruby's pret_ron_notva/pret_ron_tva).
 *
 * Two variants: "FaraTva" is the internal, VAT-exclusive line-item price -
 * used only by cart/checkout/LegacyOrderService, which compute their own
 * VAT separately for the real order/invoice tables. "CuTva" is the
 * customer-facing, VAT-inclusive price shown on every catalog page
 * (listing, detail, search) - what "pret_ron_tva" always returns in Ruby.
 */
@Service
public class PricingService {

    private final StocuriSiteViewRepository stocuriRepository;

    public PricingService(StocuriSiteViewRepository stocuriRepository) {
        this.stocuriRepository = stocuriRepository;
    }

    public BigDecimal computePriceFaraTva(StocuriSiteView produsReferinta, Integer clientId, BigDecimal discountClient) {
        StocuriSiteView clientRow = clientRowFor(produsReferinta.getId(), clientId);
        return produsReferinta.getPretRon(clientRow, clientId, discountClient);
    }

    public BigDecimal computePriceCuTva(StocuriSiteView produsReferinta, Integer clientId, BigDecimal discountClient) {
        StocuriSiteView clientRow = clientRowFor(produsReferinta.getId(), clientId);
        return produsReferinta.getPretRonCuTva(clientRow, clientId, discountClient);
    }

    public Map<Integer, BigDecimal> computePricesCuTva(List<StocuriSiteView> produseReferinta, Integer clientId, BigDecimal discountClient) {
        Map<Integer, StocuriSiteView> clientRows = clientRowsFor(produseReferinta, clientId);
        Map<Integer, BigDecimal> preturi = new HashMap<>();
        for (StocuriSiteView produs : produseReferinta) {
            preturi.put(produs.getId(), produs.getPretRonCuTva(clientRows.get(produs.getId()), clientId, discountClient));
        }
        return preturi;
    }

    private StocuriSiteView clientRowFor(Integer produsId, Integer clientId) {
        if (clientId == null || clientId <= 0) {
            return null;
        }
        return stocuriRepository.findByIdAndIdclient(produsId, clientId).orElse(null);
    }

    private Map<Integer, StocuriSiteView> clientRowsFor(List<StocuriSiteView> produse, Integer clientId) {
        if (clientId == null || clientId <= 0 || produse.isEmpty()) {
            return Map.of();
        }
        List<Integer> ids = produse.stream().map(StocuriSiteView::getId).toList();
        Map<Integer, StocuriSiteView> byId = new HashMap<>();
        for (StocuriSiteView row : stocuriRepository.findByIdInAndIdclient(ids, clientId)) {
            byId.put(row.getId(), row);
        }
        return byId;
    }
}
