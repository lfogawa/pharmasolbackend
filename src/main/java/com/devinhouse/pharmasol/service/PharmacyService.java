package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepository;

    public void save(Pharmacy pharmacy){
        pharmacyRepository.save(pharmacy);
    }


}
