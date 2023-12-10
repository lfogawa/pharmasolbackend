package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.PharmacyRequest;
import com.devinhouse.pharmasol.dtos.PharmacyResponse;
import com.devinhouse.pharmasol.exception.IllegalPageableException;
import com.devinhouse.pharmasol.exception.PharmacyNotFoundException;
import com.devinhouse.pharmasol.exception.ValidationException;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.repository.PharmacyRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Transactional
    public void save(Pharmacy pharmacy){
        pharmacyRepository.save(pharmacy);
    }

    public Page<PharmacyResponse> listAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalPageableException("Pageable cannot be null.");
        } else {
            if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
                throw new IllegalPageableException("Invalid page number or page size.");
            }

            return this.pharmacyRepository.findAll(pageable)
                    .map(PharmacyResponse::new);
        }
    }

    public Optional<PharmacyResponse> getPharmacy(Long cnpj) {
        if (pharmacyRepository.existsById(cnpj)) {
            return pharmacyRepository.findById(cnpj).map(PharmacyResponse::new);
        } else {
            throw new PharmacyNotFoundException("Pharmacy with CNPJ " + cnpj + " does not exist.");
        }
    }

    @Transactional
    public PharmacyResponse create(PharmacyRequest request) {
        if (pharmacyRepository.existsById(request.getCnpj())) {
            throw new IllegalArgumentException("Pharmacy with CNPJ " + request.getCnpj() + " already exists.");
        }

        Pharmacy pharmacy = new Pharmacy(
                request.getCnpj(),
                request.getCompanyName(),
                request.getTradingName(),
                request.getEmail(),
                request.getLandlineCellphone(),
                request.getCellphone(),
                request.getAddress());

        pharmacyRepository.save(pharmacy);

        return new PharmacyResponse(pharmacy);
    }

}