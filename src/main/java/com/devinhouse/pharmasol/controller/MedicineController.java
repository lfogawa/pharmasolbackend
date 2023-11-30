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

@RestController
@RequestMapping("/medicines")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public ResponseEntity<Page<MedicineResponse>> list(@PageableDefault(size=12, sort = "registerNumber") Pageable pageable){
        Page<MedicineResponse> response = this.medicineService.listAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MedicineRequest body){
        try {
            MedicineResponse response = this.medicineService.create(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
