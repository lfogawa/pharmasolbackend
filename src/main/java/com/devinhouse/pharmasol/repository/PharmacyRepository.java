package com.devinhouse.pharmasol.repository;

import com.devinhouse.pharmasol.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    Pharmacy findByCnpj(Long cnpj);

    boolean existsByCnpj(Long cnpj);
}