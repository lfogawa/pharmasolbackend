package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Address;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PharmacyResponse(
        @NotNull(message = "{required.field}") Long cnpj,
        @NotBlank(message = "{required.field}") String companyName,
        @NotBlank(message = "{required.field}") String tradingName,
        @NotBlank(message = "{required.field}") @Email(message = "{invalid.field}") String email,
        @NotBlank(message = "{required.field}") String landlineCellphone,
        @NotBlank(message = "{required.field}") String cellphone,
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
