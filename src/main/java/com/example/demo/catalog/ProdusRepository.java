package com.example.demo.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdusRepository extends JpaRepository<Produs, Integer> {

    List<Produs> findByOnlineTrueOrderByOrdineAsc();

    List<Produs> findByOnlineTrueAndCategorie_IdOrderByOrdineAsc(Integer categorieId);
}
