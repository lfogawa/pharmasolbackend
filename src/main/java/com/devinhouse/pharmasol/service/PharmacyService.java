package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.PharmacyResponse;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.repository.PharmacyRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepository;

    public void save(Pharmacy pharmacy){
        pharmacyRepository.save(pharmacy);
    }

    public Page<PharmacyResponse> listAll(Pageable pageable){
        return this.pharmacyRepository.findAll(pageable).map(PharmacyResponse::new);
    }

    public PharmacyResponse getPharmacy(Long cnpj){
        return this.pharmacyRepository.findById(cnpj).map(PharmacyResponse::new)
                .orElseThrow(() -> new NoSuchElementException("Pharmacy with cnpj not found: " + cnpj));
    }
}
