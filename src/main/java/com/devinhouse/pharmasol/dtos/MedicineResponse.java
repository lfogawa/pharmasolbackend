package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.enums.MedicineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicineResponse (@NotNull Integer registerNumber, @NotBlank String name, @NotBlank String laboratory, @NotBlank String dosage, String description, @NotNull Float price, @NotBlank
                                MedicineType medicineType){

    public MedicineResponse(Medicine medicine){
        this(medicine.getRegisterNumber(), medicine.getName(), medicine.getLaboratory(), medicine.getDosage(), medicine.getDescription(), medicine.getPrice(), medicine.getMedicineType());
    }

}
