package com.example.demo.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUserRepository extends JpaRepository<ClientUser, Integer> {

    // Some usernames have duplicate active rows in this data (legacy quality
    // issue, confirmed via direct SQL) - take the first by id deterministically
    // instead of failing with a NonUniqueResultException.
    Optional<ClientUser> findFirstByUtilizatorAndActivTrueOrderByIdAsc(String utilizator);
}
