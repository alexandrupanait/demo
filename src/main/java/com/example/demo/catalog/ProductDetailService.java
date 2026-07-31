package com.example.demo.catalog;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ProductDetailService {

    private static final Integer ANONYMOUS_CLIENT = 0;

    private final StocuriSiteViewRepository stocuriRepository;
    private final ProdusDetaliiRepository produsDetaliiRepository;

    public ProductDetailService(StocuriSiteViewRepository stocuriRepository, ProdusDetaliiRepository produsDetaliiRepository) {
        this.stocuriRepository = stocuriRepository;
        this.produsDetaliiRepository = produsDetaliiRepository;
    }

    public Optional<ProductDetail> getProductDetail(Integer id) {
        return stocuriRepository.findByIdAndIdclientAndOnlineTrue(id, ANONYMOUS_CLIENT)
                .map(produs -> new ProductDetail(
                        produs,
                        produs.getPretRonAnonim(),
                        produsDetaliiRepository.findById(id).orElse(null)));
    }
}
