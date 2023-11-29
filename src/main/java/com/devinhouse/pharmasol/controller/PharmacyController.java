package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pharmacies")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping
    public ResponseEntity<Page<PharmacyResponse>> list(@PageableDefault(size=12, sort = "name") Pageable pageable){
        Page<PharmacyResponse> response = this.pharmacyService.listAll(pageable);
        return ResponseEntity.ok(response);
    }
}
