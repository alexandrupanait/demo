package com.example.demo.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanciarRateRepository extends JpaRepository<FinanciarRate, Integer> {

    Optional<FinanciarRate> findByNume(String nume);
}
