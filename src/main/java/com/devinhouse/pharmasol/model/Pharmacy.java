package com.devinhouse.pharmasol.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PHARMACY")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long cnpj;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String tradingName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String landlineCellphone;
    private String cellphone;
    @Embedded
    private Address address;

}
