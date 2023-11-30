package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.MedicineResponse;
import com.devinhouse.pharmasol.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
