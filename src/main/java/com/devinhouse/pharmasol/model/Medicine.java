package com.devinhouse.pharmasol.model;

import com.devinhouse.pharmasol.model.enums.MedicineType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MEDICINE")
public class Medicine {
    @Id
    @Column(nullable = false)
    private Integer registerNumber;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String laboratory;
    @Column(nullable = false)
    private String dosage;
    private String description;
    @Column(nullable = false)
    private Float price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicineType medicineType;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks;
    @ManyToMany(mappedBy = "medicines")
    private List<Pharmacy> pharmacies;
    public Medicine() {
    }

    public Medicine(Integer registerNumber, String name, String laboratory, String dosage, String description, Float price, MedicineType medicineType) {
        this.registerNumber = registerNumber;
        this.name = name;
        this.laboratory = laboratory;
        this.dosage = dosage;
        this.description = description;
        this.price = price;
        this.medicineType = medicineType;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "registerNumber=" + registerNumber +
                ", name='" + name + '\'' +
                ", laboratory='" + laboratory + '\'' +
                ", dosage='" + dosage + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", medicineType=" + medicineType +
                '}';
    }

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
