package com.example.demo.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StocuriSiteViewRepository
        extends JpaRepository<StocuriSiteView, StocuriSiteViewId>, JpaSpecificationExecutor<StocuriSiteView> {

    List<StocuriSiteView> findByIdclientAndOnlineTrueOrderByOrdineAsc(Integer idclient);

    List<StocuriSiteView> findByIdclientAndOnlineTrueAndIdCategorieOrderByOrdineAsc(Integer idclient, Integer idCategorie);
}
