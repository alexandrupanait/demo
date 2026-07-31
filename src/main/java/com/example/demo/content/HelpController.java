package com.example.demo.content;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelpController {

    private final ContentBlockRepository contentBlockRepository;

    public HelpController(ContentBlockRepository contentBlockRepository) {
        this.contentBlockRepository = contentBlockRepository;
    }

    @GetMapping("/help")
    public String all(Model model) {
        model.addAttribute("sectiuni", contentBlockRepository.findHelpSections());
        return "help/all";
    }
}
