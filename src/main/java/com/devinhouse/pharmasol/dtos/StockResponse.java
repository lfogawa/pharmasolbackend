package com.devinhouse.pharmasol.dtos;


import com.devinhouse.pharmasol.model.Stock;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockResponse {
    private String errorMessage;
    @NotNull(message = "{required.field}")
    private Long cnpj;
    @NotNull(message = "{required.field}")
    private Long cnpjOrigin;
    @NotNull(message = "{required.field}")
    private Integer quantityOrigin;
    @NotNull(message = "{required.field}")
    private Long cnpjDestiny;
    @NotNull(message = "{required.field}")
    private Integer registerNumber;
    @NotNull(message = "{required.field}")
    private Integer quantityDestiny;
    @NotNull(message = "{required.field}")
    private Integer quantity;
    private String name;
    private LocalDateTime updateDate;

    public StockResponse() {
    }

//  Constructor to return a successful get mapping (a pharmacy's stock)
    public StockResponse(Stock stock) {
        this.registerNumber = stock.getRegisterNumber();
        this.name = stock.getMedicine().getName();
        this.quantity = stock.getQuantity();
        this.updateDate = stock.getUpdateDate();
    }

//  Constructor to return a successful put mapping (exchange medicine between two pharmacies stocks)
    public StockResponse(Integer registerNumber, Long cnpjOrigin, Integer quantityOrigin, Long cnpjDestiny, Integer quantityDestiny) {
        this.registerNumber = registerNumber;
        this.cnpjOrigin = cnpjOrigin;
        this.quantityOrigin = quantityOrigin;
        this.cnpjDestiny = cnpjDestiny;
        this.quantityDestiny = quantityDestiny;
    }

//  Constructor to return a successful delete mapping (after selling a medicine, its quantity reaches zero)
    public StockResponse(Long cnpj, Integer registerNumber, int newQuantity, LocalDateTime updateDate) {
        this.setCnpj(cnpj);
        this.setRegisterNumber(registerNumber);
        this.setQuantity(newQuantity);
        this.setUpdateDate(LocalDateTime.now());
    }

//  Constructor to return a successful post or delete mapping (add or sell medicine of a pharmacy's stock)
    public StockResponse(Long cnpj, Integer registerNumber, Integer quantity, LocalDateTime updateDate) {
        this.setCnpj(cnpj);
        this.setRegisterNumber(registerNumber);
        this.setQuantity(quantity);
        this.setUpdateDate(LocalDateTime.now());
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
