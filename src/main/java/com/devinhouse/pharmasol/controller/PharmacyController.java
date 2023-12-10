package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.PharmacyRequest;
import com.devinhouse.pharmasol.dtos.PharmacyResponse;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.service.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pharmacies")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

//  Return all pharmacies registered in the system with pages of size 12 and sort by companyName
    @GetMapping
    public ResponseEntity<Page<PharmacyResponse>> list(@PageableDefault(size=12, sort = "companyName") Pageable pageable){
        Page<PharmacyResponse> response = this.pharmacyService.listAll(pageable);
        return ResponseEntity.ok(response);
    }

//  Return one pharmacy by its CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<PharmacyResponse> getByCnpj(@PathVariable("cnpj") Long cnpj){
        Optional<PharmacyResponse> response = this.pharmacyService.getPharmacy(cnpj);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//  New pharmacy inclusion
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PharmacyRequest body){
        try {
            PharmacyResponse response = this.pharmacyService.create(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
