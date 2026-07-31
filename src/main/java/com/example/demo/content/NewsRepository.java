package com.example.demo.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {

    List<NewsItem> findByActiveTrueOrderByDateDisplayDesc();
}
