package com.example.demo.catalog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StocuriSiteViewRepository
        extends JpaRepository<StocuriSiteView, StocuriSiteViewId>, JpaSpecificationExecutor<StocuriSiteView> {

    List<StocuriSiteView> findByIdclientAndOnlineTrueOrderByOrdineAsc(Integer idclient);

    List<StocuriSiteView> findByIdclientAndOnlineTrueAndIdCategorieOrderByOrdineAsc(Integer idclient, Integer idCategorie);

    Optional<StocuriSiteView> findByIdAndIdclientAndOnlineTrue(Integer id, Integer idclient);

    @Query("""
            SELECT sv FROM StocuriSiteView sv
            WHERE sv.idclient = :idclient AND sv.online = true
              AND (lower(sv.numeAfisareInvers) LIKE lower(concat('%', :query, '%'))
                   OR lower(sv.cod) LIKE lower(concat('%', :query, '%')))
            ORDER BY sv.ordine ASC
            """)
    List<StocuriSiteView> searchByNameOrCode(@Param("idclient") Integer idclient, @Param("query") String query);
}
