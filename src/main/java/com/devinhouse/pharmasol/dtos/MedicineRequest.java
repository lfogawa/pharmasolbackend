package com.devinhouse.pharmasol.dtos;

import com.devinhouse.pharmasol.model.enums.MedicineType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicineRequest {
    @NotNull(message = "{required.field}")
    private Integer registerNumber;
    @NotBlank(message = "{required.field}")
    private String name;
    @NotBlank(message = "{required.field}")
    private String laboratory;
    @NotBlank(message = "{required.field}")
    private String dosage;
    private String description;
    @NotNull(message = "{required.field}")
    private Float price;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{required.field}")
    private MedicineType medicineType;

    public Integer getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Integer registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(MedicineType medicineType) {
        this.medicineType = medicineType;
    }
}
