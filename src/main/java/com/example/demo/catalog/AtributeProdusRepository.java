package com.example.demo.catalog;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtributeProdusRepository extends JpaRepository<AtributeProdus, Long> {

    List<AtributeProdus> findBySvidIn(Collection<Integer> svids);
}
