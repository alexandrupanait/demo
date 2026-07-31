package com.example.demo.content;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentBlockRepository extends JpaRepository<ContentBlock, Integer> {

    Optional<ContentBlock> findByNameAndActiveTrue(String name);

    @Query("""
            SELECT c FROM ContentBlock c
            WHERE c.active = true
              AND c.name LIKE 'help.%'
              AND c.name NOT LIKE 'help.menu%'
            ORDER BY c.listorder ASC
            """)
    List<ContentBlock> findHelpSections();
}
