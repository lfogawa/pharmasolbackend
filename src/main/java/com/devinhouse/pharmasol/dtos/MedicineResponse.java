package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.enums.MedicineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicineResponse (
        @NotNull(message = "{required.field}") Integer registerNumber,
        @NotBlank(message = "{required.field}") String name,
        @NotBlank(message = "{required.field}") String laboratory,
        @NotBlank(message = "{required.field}") String dosage,
        String description,
        @NotNull(message = "{required.field}") Float price,
        @NotBlank(message = "{required.field}") MedicineType medicineType){

    public MedicineResponse(Medicine medicine){
        this(
                medicine.getRegisterNumber(),
                medicine.getName(),
                medicine.getLaboratory(),
                medicine.getDosage(),
                medicine.getDescription(),
                medicine.getPrice(),
                medicine.getMedicineType());
    }

}
