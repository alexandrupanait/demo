package com.example.demo.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtributeCategorieValoareRepository extends JpaRepository<AtributeCategorieValoare, Integer> {

    List<AtributeCategorieValoare> findByIdAtributOrderByValoare(Short idAtribut);
}
