package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Address;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PharmacyResponse(
        @NotNull Long cnpj,
        @NotBlank String companyName,
        @NotBlank String tradingName,
        @NotBlank String email,
        @NotBlank String landlineCellphone,
        @NotBlank String cellphone,
        Address address
) {
    public PharmacyResponse(Pharmacy pharmacy) {
        this(
                pharmacy.getCnpj(),
                pharmacy.getCompanyName(),
                pharmacy.getTradingName(),
                pharmacy.getEmail(),
                pharmacy.getLandlineCellphone(),
                pharmacy.getCellphone(),
                pharmacy.getAddress()
        );
    }
}
