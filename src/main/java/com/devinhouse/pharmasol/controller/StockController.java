package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.StockResponse;
import com.devinhouse.pharmasol.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<List<StockResponse>> getStockByCnpj(@PathVariable Long cnpj) {
        Optional<List<StockResponse>> stockResponsesOptional = stockService.getStockByCnpj(cnpj);

        if (stockResponsesOptional.isPresent()) {
            List<StockResponse> stock = stockResponsesOptional.get();
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }
}
