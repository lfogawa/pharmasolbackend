package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.PharmacyRequest;
import com.devinhouse.pharmasol.dtos.PharmacyResponse;
import com.devinhouse.pharmasol.service.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@RestController
@RequestMapping("/pharmacies")
public class PharmacyController {
    private static final Logger logger = LogManager.getLogger(PharmacyController.class);

    @Autowired
    private PharmacyService pharmacyService;

//  Return all pharmacies registered in the system with pages of size 12 and sort by companyName
    @GetMapping
    public ResponseEntity<Page<PharmacyResponse>> list(@PageableDefault(size = 12, sort = "companyName") Pageable pageable) {
        logger.debug("Received request to list pharmacies. Pageable: {}", pageable);

        try {
            Page<PharmacyResponse> response = this.pharmacyService.listAll(pageable);
            logger.info("Pharmacy list retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error listing pharmacies", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

// Return one pharmacy by its CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<PharmacyResponse> getByCnpj(@PathVariable("cnpj") Long cnpj) {
        logger.debug("Received request to get pharmacy by CNPJ: {}", cnpj);

        try {
            Optional<PharmacyResponse> response = this.pharmacyService.getPharmacy(cnpj);

            if (response.isPresent()) {
                logger.info("Pharmacy found by CNPJ: {}", cnpj);
                return ResponseEntity.ok(response.get());
            } else {
                logger.info("Pharmacy not found by CNPJ: {}", cnpj);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error getting pharmacy by CNPJ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

// New pharmacy inclusion
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PharmacyRequest body) {
        logger.debug("Received request to create a new pharmacy. Request body: {}", body);

        try {
            PharmacyResponse response = this.pharmacyService.create(body);
            logger.info("Pharmacy created successfully. Response: {}", response);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to create a pharmacy. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating pharmacy", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
