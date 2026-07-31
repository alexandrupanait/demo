package com.example.demo.content;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class NewsController {

    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/info")
    public String all(Model model) {
        model.addAttribute("stiri", newsRepository.findByActiveTrueOrderByDateDisplayDesc());
        return "news/all";
    }

    @GetMapping("/info/{id}")
    public String details(@PathVariable Long id, Model model) {
        NewsItem item = newsRepository.findById(id)
                .filter(n -> Boolean.TRUE.equals(n.getActive()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("stire", item);
        return "news/details";
    }
}
