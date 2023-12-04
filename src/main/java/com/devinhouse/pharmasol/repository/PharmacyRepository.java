package com.devinhouse.pharmasol.repository;

import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    Pharmacy findByCnpj(Long cnpj);
}