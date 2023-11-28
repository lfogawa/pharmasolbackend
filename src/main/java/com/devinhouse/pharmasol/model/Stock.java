package com.devinhouse.pharmasol.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK")
@IdClass(IdStock.class)
public class Stock {
    @Id
    @Column(nullable = false)
    private Long cnpj;
    @Id
    @Column(nullable = false)
    private Integer registerNumber;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime updateDate;

    public Stock() {
    }

    @Override
    public String toString() {
        return "Stock{" +
                "cnpj=" + cnpj +
                ", registerNumber=" + registerNumber +
                ", quantity=" + quantity +
                ", updateDate=" + updateDate +
                '}';
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Integer registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}