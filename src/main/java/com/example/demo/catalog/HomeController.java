package com.example.demo.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.content.NewsRepository;

@Controller
public class HomeController {

    private final NewsRepository newsRepository;

    public HomeController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("ultimaStire", newsRepository.findByActiveTrueOrderByDateDisplayDesc()
                .stream().findFirst().orElse(null));
        return "home";
    }
}
