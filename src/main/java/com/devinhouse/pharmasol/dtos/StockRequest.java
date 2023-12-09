package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Stock;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockRequest {
    @NotNull(message = "{required.field}")
    private Long cnpjOrigin;
    @NotNull(message = "{required.field}")
    private Long cnpjDestiny;
    @NotNull(message = "{required.field}")
    private Long cnpj;
    @NotNull(message = "{required.field}")
    private Integer registerNumber;
    @NotNull(message = "{required.field}")
    private Integer quantity;
    private LocalDateTime updateDate;
    private String name;

    public StockRequest() {
    }

    public StockRequest(Stock stock) {
        this.cnpj = stock.getCnpj();
        this.name = stock.getMedicine().getName();
        this.registerNumber = stock.getRegisterNumber();
        this.quantity = stock.getQuantity();
        this.updateDate = LocalDateTime.now();
    }

    public StockRequest(Long cnpjOrigin, Long cnpjDestiny, Integer registerNumber, Integer quantity) {
        this.cnpjOrigin = cnpjOrigin;
        this.cnpjDestiny = cnpjDestiny;
        this.registerNumber = registerNumber;
        this.quantity = quantity;
    }

    public StockRequest(Long cnpj, Integer registerNumber, Integer quantity) {
        this.cnpj = cnpj;
        this.registerNumber = registerNumber;
        this.quantity = quantity;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public Long getCnpjOrigin() {
        return cnpjOrigin;
    }

    public Long getCnpjDestiny() {
        return cnpjDestiny;
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
