package com.example.demo.content;

/** One entry in the left "Ajutor" sidebar - mirrors the old site's help/_menu.html.erb. */
public class HelpMenuLink {

    private final String label;
    private final String href;
    private final boolean sub;
    private final boolean active;

    public HelpMenuLink(String label, String href, boolean sub, boolean active) {
        this.label = label;
        this.href = href;
        this.sub = sub;
        this.active = active;
    }

    public String getLabel() {
        return label;
    }

    public String getHref() {
        return href;
    }

    public boolean isSub() {
        return sub;
    }

    public boolean isActive() {
        return active;
    }
}
