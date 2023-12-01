package com.devinhouse.pharmasol.dtos;


import com.devinhouse.pharmasol.model.Stock;

import java.time.LocalDateTime;

public class StockResponse {
    private Integer registerNumber;

    private String name;

    private Integer quantity;

    private LocalDateTime updateDate;


    public StockResponse() {
    }

    public StockResponse(Stock stock) {
        this.registerNumber = stock.getRegisterNumber();
        this.name = stock.getMedicine().getName();
        this.quantity = stock.getQuantity();
        this.updateDate = stock.getUpdateDate();
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

    public String getName() {
        return name;
    }
}
