package com.example.demo.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategorieProdusRepository extends JpaRepository<CategorieProdus, Integer> {

    // A category is a nav root if it's online and its parent either doesn't
    // exist or isn't itself online (the data has a "Fara"/none sentinel and
    // an internal wrapper category above the real top-level categories).
    @Query("""
            SELECT c FROM CategorieProdus c
            WHERE c.online = true
              AND (c.idParinte IS NULL OR NOT EXISTS (
                  SELECT 1 FROM CategorieProdus p WHERE p.id = c.idParinte AND p.online = true
              ))
            ORDER BY c.nrOrdine
            """)
    List<CategorieProdus> findTopLevel();

    List<CategorieProdus> findByIdParinteAndOnlineTrueOrderByNrOrdineAsc(Integer idParinte);
}
