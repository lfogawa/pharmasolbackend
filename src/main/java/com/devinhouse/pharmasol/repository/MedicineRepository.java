package com.devinhouse.pharmasol.repository;

import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    Medicine findByRegisterNumber(Integer registerNumber);
}
