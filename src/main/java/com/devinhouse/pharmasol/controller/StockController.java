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

//  Return a pharmacy's stock by its CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<List<StockResponse>> getStockByCnpj(@PathVariable Long cnpj) {
        Optional<List<StockResponse>> stockResponsesOptional = stockService.getStockByCnpj(cnpj);

//      If pharmacy's stock is present, return pharmacy's stock with medicine quantity updated; otherwise, return error
        if (stockResponsesOptional.isPresent()) {
            List<StockResponse> stock = stockResponsesOptional.get();
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }

//  Add medicine in a pharmacy's stock
    @PostMapping
    public ResponseEntity<StockResponse> addMedicine(@RequestBody StockRequest stockRequest) {
        try {
//          Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
            validateRequest(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.addMedicine(
                    stockRequest.getCnpj(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

//          If pharmacy's stock is present, return pharmacy's stock with medicine quantity updated; otherwise, return error
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

//  Sell medicine in a pharmacy's stock
    @DeleteMapping
    public ResponseEntity<StockResponse> sellMedicine(@RequestBody StockRequest stockRequest) {
        try {
//          Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
            validateRequest(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.sellMedicine(
                    stockRequest.getCnpj(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

//          If pharmacy's stock is present, return medicine's quantity updated
            if (stockResponseOptional.isPresent()) {
                StockResponse stock = stockResponseOptional.get();
                return ResponseEntity.ok(stock);
            }
        }
//      If pharmacy's stock isn't present, return exception
        catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse());
    }

//  Exchange medicine between two pharmacies stock
    @PutMapping
    public ResponseEntity<StockResponse> exchangeMedicine(@RequestBody StockRequest stockRequest) {
        try {
//          Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
            validateRequestExchangeMedicine(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.exchangeMedicine(
                    stockRequest.getCnpjOrigin(),
                    stockRequest.getCnpjDestiny(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

//          If pharmacy's stock is present, return both pharmacies stocks with medicine quantities updated; otherwise, return error
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

//  Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
    private void validateRequestExchangeMedicine(StockRequest stockRequest) throws ValidationException {
        if (stockRequest.getCnpjOrigin() == null || stockRequest.getCnpjDestiny() == null || stockRequest.getRegisterNumber() == null || stockRequest.getQuantity() == null) {
            throw new ValidationException("All fields are obligatory.");
        }

        if (stockRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be an positive number, bigger than zero.");
        }
    }

//  Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
    private void validateRequest(StockRequest stockRequest) throws ValidationException {
        if (stockRequest.getCnpj() == null || stockRequest.getRegisterNumber() == null || stockRequest.getQuantity() == null) {
            throw new ValidationException("All fields are obligatory.");
        }

        if (stockRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be an positive number, bigger than zero.");
        }
    }
}
