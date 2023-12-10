package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.MedicineResponse;
import com.devinhouse.pharmasol.dtos.MedicineRequest;
import com.devinhouse.pharmasol.exception.MedicineAlreadyExistsException;
import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.repository.MedicineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

//  Method to save medicines when post mapping (initialization controller)
    @Transactional
    public void save(Medicine medicine){
        medicineRepository.save(medicine);
    }

//  Method to list all medicines when get mapping (medicine controller)
    public Page<MedicineResponse> listAll(Pageable pageable){
        return this.medicineRepository.findAll(pageable).map(MedicineResponse::new);
    }

//  Method to create a medicine when post mapping (medicine controller)
    @Transactional
    public MedicineResponse create(MedicineRequest request) {
        if (medicineRepository.existsById(request.getRegisterNumber())) {
            throw new MedicineAlreadyExistsException("Medicine with Register Number " + request.getRegisterNumber() + " already exists.");
        }

        Medicine medicine = new Medicine(
                request.getRegisterNumber(),
                request.getName(),
                request.getLaboratory(),
                request.getDosage(),
                request.getDescription(),
                request.getPrice(),
                request.getMedicineType());

        medicineRepository.save(medicine);

        return new MedicineResponse(medicine);
    }
}
