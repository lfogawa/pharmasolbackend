package com.devinhouse.pharmasol.dtos;


import com.devinhouse.pharmasol.model.Stock;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockResponse {
    private String errorMessage;
    private Long cnpj;
    private Long cnpjOrigin;
    private Integer quantityOrigin;
    private Long cnpjDestiny;
    private Integer registerNumber;
    private Integer quantityDestiny;
    private Integer quantity;
    private String name;
    private LocalDateTime updateDate;

    public StockResponse() {
    }

    public StockResponse(Stock stock) {
        this.registerNumber = stock.getRegisterNumber();
        this.name = stock.getMedicine().getName();
        this.quantity = stock.getQuantity();
        this.updateDate = stock.getUpdateDate();
    }

    public StockResponse(Integer registerNumber, Long cnpjOrigin, Integer quantityOrigin, Long cnpjDestiny, Integer quantityDestiny) {
        this.registerNumber = registerNumber;
        this.cnpjOrigin = cnpjOrigin;
        this.quantityOrigin = quantityOrigin;
        this.cnpjDestiny = cnpjDestiny;
        this.quantityDestiny = quantityDestiny;
    }

    public static StockResponse sellMedicineResponse(Long cnpj, Integer registerNumber, Integer quantity, LocalDateTime updateDate) {
        StockResponse response = new StockResponse();
        response.setCnpj(cnpj);
        response.setRegisterNumber(registerNumber);
        response.setQuantity(quantity);
        response.setUpdateDate(updateDate);
        return response;
    }

    public StockResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public void setRegisterNumber(Integer registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Long getCnpjOrigin() {
        return cnpjOrigin;
    }

    public Integer getQuantityOrigin() {
        return quantityOrigin;
    }

    public Long getCnpjDestiny() {
        return cnpjDestiny;
    }

    public Integer getQuantityDestiny() {
        return quantityDestiny;
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
