package com.devinhouse.pharmasol.repository;

import com.devinhouse.pharmasol.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    public boolean existsByCnpj(Long cnpj);
}