package com.example.demo.catalog;

import java.util.List;

public class CategoryNode {

    private final Integer id;
    private final String nume;
    private final List<CategoryNode> children;

    public CategoryNode(Integer id, String nume, List<CategoryNode> children) {
        this.id = id;
        this.nume = nume;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public List<CategoryNode> getChildren() {
        return children;
    }
}
