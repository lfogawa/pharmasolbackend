package com.devinhouse.pharmasol.model;

import com.devinhouse.pharmasol.model.enums.MedicineType;
import jakarta.persistence.*;

@Entity
@Table(name = "MEDICINE")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
