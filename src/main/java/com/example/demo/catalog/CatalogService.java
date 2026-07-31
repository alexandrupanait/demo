package com.example.demo.catalog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CatalogService {

    private final CategorieProdusRepository categorieProdusRepository;

    public CatalogService(CategorieProdusRepository categorieProdusRepository) {
        this.categorieProdusRepository = categorieProdusRepository;
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

    /**
     * A category plus every descendant id, depth-first - used to list all
     * products under a parent category (e.g. "UPS"), which itself usually
     * has no products of its own, only through its subcategories.
     */
    public List<Integer> getCategoryAndDescendantIds(Integer categorieId) {
        List<Integer> ids = new ArrayList<>();
        collectDescendants(categorieId, ids);
        return ids;
    }

    private void collectDescendants(Integer categorieId, List<Integer> ids) {
        ids.add(categorieId);
        for (CategorieProdus child : categorieProdusRepository.findByIdParinteAndOnlineTrueOrderByNrOrdineAsc(categorieId)) {
            collectDescendants(child.getId(), ids);
        }
    }

    /**
     * Ancestor chain for the breadcrumb, root-to-leaf. Walks all the way up
     * to the real root, then drops leading "hidden wrapper" ancestors (e.g.
     * "Intern") using the exact same rule getCategoryTree() uses to hide
     * them: a top-level category (per findTopLevel) that has children gets
     * skipped and its children promoted instead - so the breadcrumb starts
     * at the first ancestor that's actually visible in the sidebar tree.
     */
    public List<CategorieProdus> getBreadcrumb(Integer categorieId) {
        LinkedList<CategorieProdus> chain = new LinkedList<>();
        Integer currentId = categorieId;
        while (currentId != null) {
            CategorieProdus categorie = categorieProdusRepository.findById(currentId).orElse(null);
            if (categorie == null) {
                break;
            }
            chain.addFirst(categorie);
            currentId = categorie.getIdParinte();
        }
        while (!chain.isEmpty() && isHiddenWrapper(chain.getFirst())) {
            chain.removeFirst();
        }
        return chain;
    }

    private boolean isHiddenWrapper(CategorieProdus categorie) {
        Integer parentId = categorie.getIdParinte();
        boolean parentOnline = parentId != null
                && categorieProdusRepository.findById(parentId).map(CategorieProdus::getOnline).orElse(false);
        boolean isTopLevel = !parentOnline;
        boolean hasChildren = !categorieProdusRepository.findByIdParinteAndOnlineTrueOrderByNrOrdineAsc(categorie.getId()).isEmpty();
        return isTopLevel && hasChildren;
    }
}
