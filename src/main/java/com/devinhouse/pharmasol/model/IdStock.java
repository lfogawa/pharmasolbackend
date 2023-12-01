package com.devinhouse.pharmasol.model;

import java.io.Serializable;
import java.util.Objects;

public class IdStock implements Serializable {
    private Long cnpj;
    private Integer registerNumber;

    public IdStock() {
    }
    public IdStock(Long cnpj) {
        this.cnpj = cnpj;
    }

    public IdStock(Long cnpj, Integer registerNumber) {
        this.cnpj = cnpj;
        this.registerNumber = registerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdStock idStock = (IdStock) o;
        return Objects.equals(cnpj, idStock.cnpj) && Objects.equals(registerNumber, idStock.registerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, registerNumber);
    }
}
