package com.example.demo.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Integer> {

    boolean existsByFirmaIgnoreCase(String firma);

    boolean existsByCodFiscal(String codFiscal);
}
