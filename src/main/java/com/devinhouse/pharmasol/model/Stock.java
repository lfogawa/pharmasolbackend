package com.devinhouse.pharmasol.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "STOCK")
@IdClass(IdStock.class)
public class Stock {
    @Id
    @Column(nullable = false)
    private Long cnpj;
    @Id
    @Column(nullable = false)
    private Integer registerNumber;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime updateDate;
}