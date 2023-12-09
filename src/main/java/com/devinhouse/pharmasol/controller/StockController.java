package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.StockRequest;
import com.devinhouse.pharmasol.dtos.StockResponse;
import com.devinhouse.pharmasol.exception.ValidationException;
import com.devinhouse.pharmasol.model.Stock;
import com.devinhouse.pharmasol.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<StockResponse> addMedicine(@RequestBody StockRequest stockRequest) {
        try {
            validateRequest(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.addMedicine(
                    stockRequest.getCnpj(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

            if (stockResponseOptional.isPresent()) {
                StockResponse stock = stockResponseOptional.get();
                return ResponseEntity.status(HttpStatus.CREATED).body(stock);

            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<StockResponse> sellMedicine(@RequestBody StockRequest stockRequest) {
        try {
            validateRequest(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.sellMedicine(
                    stockRequest.getCnpj(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

            if (stockResponseOptional.isPresent()) {
                StockResponse stock = stockResponseOptional.get();
                return ResponseEntity.ok(stock);
            }
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse());
    }

    @PutMapping
    public ResponseEntity<StockResponse> exchangeMedicine(@RequestBody StockRequest stockRequest) {
        try {
            validateRequestExchangeMedicine(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.exchangeMedicine(
                    stockRequest.getCnpjOrigin(),
                    stockRequest.getCnpjDestiny(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

            if (stockResponseOptional.isPresent()) {
                StockResponse stock = stockResponseOptional.get();
                return ResponseEntity.status(HttpStatus.CREATED).body(stock);
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse(e.getMessage()));
        }
    }

    private void validateRequestExchangeMedicine(StockRequest stockRequest) throws ValidationException {
        if (stockRequest.getCnpjOrigin() == null || stockRequest.getCnpjDestiny() == null || stockRequest.getRegisterNumber() == null || stockRequest.getQuantity() == null) {
            throw new ValidationException("All fields are obligatory.");
        }

        if (stockRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be an positive number, bigger than zero.");
        }
    }
    private void validateRequest(StockRequest stockRequest) throws ValidationException {
        if (stockRequest.getCnpj() == null || stockRequest.getRegisterNumber() == null || stockRequest.getQuantity() == null) {
            throw new ValidationException("All fields are obligatory.");
        }

        if (stockRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be an positive number, bigger than zero.");
        }
    }
}
