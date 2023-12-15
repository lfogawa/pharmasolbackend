package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.MedicineRequest;
import com.devinhouse.pharmasol.dtos.MedicineResponse;
import com.devinhouse.pharmasol.service.MedicineService;
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

@RestController
@RequestMapping("/medicines")
public class MedicineController {
    private static final Logger logger = LogManager.getLogger(MedicineController.class);

    @Autowired
    private MedicineService medicineService;

//  Return all medicines registered in the system with pages of size 12 and sort by registerNumber
    @GetMapping
    public ResponseEntity<Page<MedicineResponse>> list(@PageableDefault(size=12, sort = "registerNumber") Pageable pageable){
        logger.debug("Received request to list medicines. Pageable: {}", pageable);

        try {
            Page<MedicineResponse> response = this.medicineService.listAll(pageable);
            logger.info("Medicine list retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error listing medicines", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//  New medicine inclusion
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MedicineRequest body){
        logger.debug("Received request to create a new medicine. Request body: {}", body);

        try {
            MedicineResponse response = this.medicineService.create(body);
            logger.info("Medicine created successfully. Response: {}", response);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request to create a medicine. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating medicine", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
