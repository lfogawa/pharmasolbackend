package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.dtos.StockRequest;
import com.devinhouse.pharmasol.dtos.StockResponse;
import com.devinhouse.pharmasol.exception.ValidationException;
import com.devinhouse.pharmasol.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private static final Logger logger = LogManager.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

// Return a pharmacy's stock by its CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<List<StockResponse>> getStockByCnpj(@PathVariable Long cnpj) {
        logger.debug("Received request to get pharmacy's stock by CNPJ: {}", cnpj);

        try {
            Optional<List<StockResponse>> stockResponsesOptional = stockService.getStockByCnpj(cnpj);

            if (stockResponsesOptional.isPresent()) {
                List<StockResponse> stock = stockResponsesOptional.get();
                logger.info("Pharmacy's stock retrieved successfully. CNPJ: {}", cnpj);
                return ResponseEntity.ok(stock);
            } else {
                logger.info("Pharmacy's stock not found by CNPJ: {}", cnpj);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
        } catch (Exception e) {
            logger.error("Error getting pharmacy's stock by CNPJ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

// Add medicine in a pharmacy's stock
    @PostMapping
    public ResponseEntity<StockResponse> addMedicine(@RequestBody StockRequest stockRequest) {
        logger.debug("Received request to add medicine in pharmacy's stock. Request body: {}", stockRequest);

        try {
            validateRequest(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.addMedicine(
                    stockRequest.getCnpj(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

            if (stockResponseOptional.isPresent()) {
                StockResponse stock = stockResponseOptional.get();
                logger.info("Medicine added to pharmacy's stock successfully. Response: {}", stock);
                return ResponseEntity.status(HttpStatus.CREATED).body(stock);
            } else {
                logger.error("Error adding medicine to pharmacy's stock. Response is empty.");
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
        } catch (ValidationException e) {
            logger.warn("Invalid request to add medicine. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error adding medicine to pharmacy's stock", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

// Sell medicine in a pharmacy's stock
    @DeleteMapping
    public ResponseEntity<StockResponse> sellMedicine(@RequestBody StockRequest stockRequest) {
        logger.debug("Received request to sell medicine in pharmacy's stock. Request body: {}", stockRequest);

        try {
            validateRequest(stockRequest);

            Optional<StockResponse> stockResponseOptional = stockService.sellMedicine(
                    stockRequest.getCnpj(),
                    stockRequest.getRegisterNumber(),
                    stockRequest.getQuantity()
            );

            if (stockResponseOptional.isPresent()) {
                StockResponse stock = stockResponseOptional.get();
                logger.info("Medicine sold from pharmacy's stock successfully. Response: {}", stock);
                return ResponseEntity.ok(stock);
            }
        } catch (ValidationException e) {
            logger.warn("Invalid request to sell medicine. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error selling medicine from pharmacy's stock", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse());
        }

        logger.error("Error selling medicine from pharmacy's stock. Response is empty.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse());
    }

// Exchange medicine between two pharmacies stock
    @PutMapping
    public ResponseEntity<StockResponse> exchangeMedicine(@RequestBody StockRequest stockRequest) {
        logger.debug("Received request to exchange medicine between two pharmacies. Request body: {}", stockRequest);

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
                logger.info("Medicine exchanged between pharmacies successfully. Response: {}", stock);
                return ResponseEntity.status(HttpStatus.CREATED).body(stock);
            } else {
                logger.error("Error exchanging medicine between pharmacies. Response is empty.");
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }
        } catch (ValidationException e) {
            logger.warn("Invalid request to exchange medicine between pharmacies. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StockResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error exchanging medicine between pharmacies", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

// Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
    private void validateRequestExchangeMedicine(StockRequest stockRequest) throws ValidationException {
        if (stockRequest.getCnpjOrigin() == null || stockRequest.getCnpjDestiny() == null || stockRequest.getRegisterNumber() == null || stockRequest.getQuantity() == null) {
            throw new ValidationException("All fields are obligatory.");
        }

        if (stockRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be a positive number, bigger than zero.");
        }
    }

// Validation ensuring all obligatory fields are filled and quantity is a positive number, bigger than zero
    private void validateRequest(StockRequest stockRequest) throws ValidationException {
        if (stockRequest.getCnpj() == null || stockRequest.getRegisterNumber() == null || stockRequest.getQuantity() == null) {
            throw new ValidationException("All fields are obligatory.");
        }

        if (stockRequest.getQuantity() <= 0) {
            throw new ValidationException("Quantity must be a positive number, bigger than zero.");
        }
    }
}
