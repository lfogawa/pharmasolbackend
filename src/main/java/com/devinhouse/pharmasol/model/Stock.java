package com.devinhouse.pharmasol.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "STOCK")
@IdClass(IdStock.class)
public class Stock implements Serializable {
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registerNumber", referencedColumnName = "registerNumber", insertable = false, updatable = false)
    private Medicine medicine;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cnpj", referencedColumnName = "cnpj", insertable = false, updatable = false)
    private Pharmacy pharmacy;

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

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
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

    public Medicine getMedicine() {
        return medicine;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void save(Stock newStock) {
    }
}