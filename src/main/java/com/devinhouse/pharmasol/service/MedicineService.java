package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.MedicineResponse;
import com.devinhouse.pharmasol.dtos.PharmacyResponse;
import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    public void save(Medicine medicine){
        medicineRepository.save(medicine);
    }

    public Page<MedicineResponse> listAll(Pageable pageable){
        return this.medicineRepository.findAll(pageable).map(MedicineResponse::new);
    }
}
