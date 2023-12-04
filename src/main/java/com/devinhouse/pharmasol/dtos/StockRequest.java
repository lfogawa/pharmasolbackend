package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Stock;

import java.time.LocalDateTime;

public class StockRequest {
    private Long cnpj;
    private Integer registerNumber;

    private Integer quantity;

    private LocalDateTime updateDate;


    public StockRequest() {
    }

    public StockRequest(Stock stock) {
        this.cnpj = stock.getCnpj();
        this.registerNumber = stock.getRegisterNumber();
        this.quantity = stock.getQuantity();
    }

    public Long getCnpj() {
        return cnpj;
    }

    public Integer getRegisterNumber() {
        return registerNumber;
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
