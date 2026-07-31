package com.example.demo.content;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/help/{section}")
    public String section(@PathVariable String section, Model model) {
        return renderSection(section, model);
    }

    @GetMapping("/help/{supersection}/{section}")
    public String subsection(@PathVariable String supersection, @PathVariable String section, Model model) {
        return renderSection(supersection + "." + section, model);
    }

    /**
     * Mirrors the old HelpController#all: a section key like "termeni" or
     * "ralonline.invoices" maps to the content row "help." + key, and the
     * sidebar is built from the "help.menu.*" rows (their own content_ro
     * holds the short label text, not a page body - see help/_menu.html.erb).
     */
    private String renderSection(String sectionKey, Model model) {
        model.addAttribute("continut", contentBlockRepository.findByNameAndActiveTrue("help." + sectionKey).orElse(null));
        model.addAttribute("menu", buildMenu(sectionKey));
        return "help/page";
    }

    // Legacy-only: the real site hides this entry unless the account has the
    // special ERICSSON/VDSL project permission - a niche account type this
    // rebuild doesn't have, so it's simplest and correct to always exclude it.
    private static final String VDSL_PROJECT_MENU_ITEM = "help.menu.ralonline.vdsl_project";

    private List<HelpMenuLink> buildMenu(String sectionActiva) {
        List<HelpMenuLink> menu = new ArrayList<>();
        for (ContentBlock item : contentBlockRepository.findHelpMenuItems()) {
            if (VDSL_PROJECT_MENU_ITEM.equals(item.getName())) {
                continue;
            }
            String key = item.getName().substring("help.menu.".length());
            boolean sub = key.contains(".");
            String href = "/help/" + key.replace('.', '/');
            menu.add(new HelpMenuLink(item.getContentRo(), href, sub, key.equals(sectionActiva)));
        }
        return menu;
    }
}
