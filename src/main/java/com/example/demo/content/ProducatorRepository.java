package com.example.demo.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducatorRepository extends JpaRepository<Producator, Integer> {

    List<Producator> findByOnlineTrueOrderByOrdineWebAsc();
}
