package com.example.demo.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByIdClientOrderByDataDesc(Integer idClient);
}
