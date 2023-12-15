package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.PharmacyRequest;
import com.devinhouse.pharmasol.dtos.PharmacyResponse;
import com.devinhouse.pharmasol.exception.IllegalPageableException;
import com.devinhouse.pharmasol.exception.PharmacyNotFoundException;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.repository.PharmacyRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Service
public class PharmacyService {
    private static final Logger logger = LogManager.getLogger(PharmacyService.class);

    @Autowired
    private PharmacyRepository pharmacyRepository;

// Method to save pharmacies when post mapping (initialization controller)
    @Transactional
    public void save(Pharmacy pharmacy) {
        pharmacyRepository.save(pharmacy);
        logger.info("Pharmacy saved successfully. CNPJ: {}", pharmacy.getCnpj());
    }

// Method to list all pharmacies when get mapping (pharmacy controller)
    public Page<PharmacyResponse> listAll(Pageable pageable) {
        try {
            if (pageable == null) {
                throw new IllegalPageableException("Pageable cannot be null.");
            } else {
                if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
                    throw new IllegalPageableException("Invalid page number or page size.");
                }

                Page<PharmacyResponse> result = this.pharmacyRepository.findAll(pageable)
                        .map(PharmacyResponse::new);
                logger.info("Listed all pharmacies. Total items: {}", result.getTotalElements());
                return result;
            }
        } catch (IllegalPageableException e) {
            logger.error("Error listing pharmacies. {}", e.getMessage());
            throw e;
        }
    }

// Method to return a pharmacy's data when get mapping (pharmacy controller)
    public Optional<PharmacyResponse> getPharmacy(Long cnpj) {
        try {
            if (pharmacyRepository.existsById(cnpj)) {
                Optional<PharmacyResponse> result = pharmacyRepository.findById(cnpj).map(PharmacyResponse::new);
                logger.info("Retrieved pharmacy by CNPJ: {}", cnpj);
                return result;
            } else {
                throw new PharmacyNotFoundException("Pharmacy with CNPJ " + cnpj + " does not exist.");
            }
        } catch (PharmacyNotFoundException e) {
            logger.error("Error retrieving pharmacy. {}", e.getMessage());
            throw e;
        }
    }

// Method to create a pharmacy when post mapping (pharmacy controller)
    @Transactional
    public PharmacyResponse create(PharmacyRequest request) {
        try {
            if (pharmacyRepository.existsById(request.getCnpj())) {
                logger.warn("Attempted to create a duplicate pharmacy with CNPJ: {}", request.getCnpj());
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
            logger.info("Pharmacy created successfully. CNPJ: {}", pharmacy.getCnpj());
            return new PharmacyResponse(pharmacy);
        } catch (Exception e) {
            logger.error("Error creating pharmacy. {}", e.getMessage());
            throw e;
        }
    }
}