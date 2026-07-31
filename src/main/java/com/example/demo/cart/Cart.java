package com.example.demo.cart;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/** Session-held shopping cart: just product id -> quantity, resolved against live product data on render. */
public class Cart implements Serializable {

    private final Map<Integer, Integer> items = new LinkedHashMap<>();

    public void add(Integer produsId) {
        items.merge(produsId, 1, Integer::sum);
    }

    public void setQuantity(Integer produsId, int cantitate) {
        if (cantitate <= 0) {
            items.remove(produsId);
        } else {
            items.put(produsId, cantitate);
        }
    }

    public void remove(Integer produsId) {
        items.remove(produsId);
    }

    public void clear() {
        items.clear();
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
