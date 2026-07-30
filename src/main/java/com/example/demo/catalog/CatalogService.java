package com.example.demo.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    private final ProdusRepository produsRepository;
    private final CategorieProdusRepository categorieProdusRepository;

    public CatalogService(ProdusRepository produsRepository, CategorieProdusRepository categorieProdusRepository) {
        this.produsRepository = produsRepository;
        this.categorieProdusRepository = categorieProdusRepository;
    }

    public List<Produs> listOnlineProducts(Integer categorieId) {
        if (categorieId == null) {
            return produsRepository.findByOnlineTrueOrderByOrdineAsc();
        }
        return produsRepository.findByOnlineTrueAndCategorie_IdOrderByOrdineAsc(categorieId);
    }

    public List<CategoryNode> getCategoryTree() {
        // A top-level category that itself has children is just an internal
        // wrapper (e.g. "Intern") - hide it and promote its children to
        // top-level instead. A top-level category with no children is a real
        // leaf category and stays visible as-is.
        List<CategoryNode> nodes = new ArrayList<>();
        for (CategorieProdus root : categorieProdusRepository.findTopLevel()) {
            List<CategorieProdus> children = categorieProdusRepository.findByIdParinteAndOnlineTrueOrderByNrOrdineAsc(root.getId());
            if (children.isEmpty()) {
                nodes.add(new CategoryNode(root.getId(), root.getNume(), List.of()));
            } else {
                nodes.addAll(buildTree(children));
            }
        }
        return nodes;
    }

    private List<CategoryNode> buildTree(List<CategorieProdus> categories) {
        return categories.stream()
                .map(c -> new CategoryNode(
                        c.getId(),
                        c.getNume(),
                        buildTree(categorieProdusRepository.findByIdParinteAndOnlineTrueOrderByNrOrdineAsc(c.getId()))))
                .toList();
    }
}
