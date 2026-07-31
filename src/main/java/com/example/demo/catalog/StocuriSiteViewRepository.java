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

    List<StocuriSiteView> findByIdclientAndOnlineTrueAndIdCategorieInOrderByOrdineAsc(Integer idclient, List<Integer> idCategorii);

    Optional<StocuriSiteView> findByIdAndIdclientAndOnlineTrue(Integer id, Integer idclient);

    // The sparse, per-client negotiated-price row (no online filter - it
    // exists purely to carry a price override, see StocuriSiteView#getPretRon).
    Optional<StocuriSiteView> findByIdAndIdclient(Integer id, Integer idclient);

    List<StocuriSiteView> findByIdInAndIdclient(List<Integer> ids, Integer idclient);

    // Mirrors the legacy "no category selected" branch exactly, including
    // its lack of an online filter: top_produse is a curated top-10
    // best-sellers list, joined by product code (not id - most svid values
    // in top_produse are stale and don't match current stocuri_site_view
    // ids, same as the original Ruby SQL). The list itself is stale too -
    // all 10 entries are currently offline - but the user chose exact
    // parity with the real site over silently filtering them out.
    @Query(value = """
            SELECT s.* FROM stocuri_site_view s
            JOIN top_produse t ON t.cod = s.cod
            WHERE s.idclient = :idclient
            ORDER BY t.place ASC
            """, nativeQuery = true)
    List<StocuriSiteView> findTopSellers(@Param("idclient") Integer idclient);

    @Query("""
            SELECT sv FROM StocuriSiteView sv
            WHERE sv.idclient = :idclient AND sv.online = true
              AND (lower(sv.numeAfisareInvers) LIKE lower(concat('%', :query, '%'))
                   OR lower(sv.cod) LIKE lower(concat('%', :query, '%')))
            ORDER BY sv.ordine ASC
            """)
    List<StocuriSiteView> searchByNameOrCode(@Param("idclient") Integer idclient, @Param("query") String query);
}
