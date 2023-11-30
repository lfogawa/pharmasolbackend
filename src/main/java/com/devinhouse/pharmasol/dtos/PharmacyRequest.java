package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PharmacyRequest{
    @NotNull
    Long cnpj;
    @NotBlank
    String companyName;
    @NotBlank
    String tradingName;
    @NotBlank
    String email;
    String landlineCellphone;
    @NotBlank
    String cellphone;
    Address address;

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLandlineCellphone() {
        return landlineCellphone;
    }

    public void setLandlineCellphone(String landlinecellphone) {
        this.landlineCellphone = landlinecellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
