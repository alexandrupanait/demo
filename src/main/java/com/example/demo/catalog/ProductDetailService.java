package com.example.demo.catalog;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProductDetailService {

    private static final Integer ANONYMOUS_CLIENT = 0;

    private final StocuriSiteViewRepository stocuriRepository;
    private final ProdusDetaliiRepository produsDetaliiRepository;
    private final PricingService pricingService;

    public ProductDetailService(StocuriSiteViewRepository stocuriRepository, ProdusDetaliiRepository produsDetaliiRepository,
            PricingService pricingService) {
        this.stocuriRepository = stocuriRepository;
        this.produsDetaliiRepository = produsDetaliiRepository;
        this.pricingService = pricingService;
    }

    public Optional<ProductDetail> getProductDetail(Integer id, Integer clientId, BigDecimal discountClient) {
        return stocuriRepository.findByIdAndIdclientAndOnlineTrue(id, ANONYMOUS_CLIENT)
                .map(produs -> new ProductDetail(
                        produs,
                        pricingService.computePriceCuTva(produs, clientId, discountClient),
                        produsDetaliiRepository.findById(id).orElse(null)));
    }
}
