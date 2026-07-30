package com.example.demo.catalog;

import java.io.Serializable;
import java.util.Objects;

public class StocuriSiteViewId implements Serializable {

    private Integer id;
    private Integer idclient;

    public StocuriSiteViewId() {
    }

    public StocuriSiteViewId(Integer id, Integer idclient) {
        this.id = id;
        this.idclient = idclient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StocuriSiteViewId other)) {
            return false;
        }
        return Objects.equals(id, other.id) && Objects.equals(idclient, other.idclient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idclient);
    }
}
