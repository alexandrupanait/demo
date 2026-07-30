package com.example.demo.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtributeCategorieRepository extends JpaRepository<AtributeCategorie, Integer> {

    List<AtributeCategorie> findByCategorieIdAndOnlineTrueOrderByNume(Integer categorieId);
}
