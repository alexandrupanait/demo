package com.example.demo.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutieRepository extends JpaRepository<Solutie, Short> {

    List<Solutie> findByOnlineTrueOrderByOrdineAsc();
}
