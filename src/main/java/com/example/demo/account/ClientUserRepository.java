package com.example.demo.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUserRepository extends JpaRepository<ClientUser, Integer> {

    Optional<ClientUser> findByUtilizatorAndActivTrue(String utilizator);
}
