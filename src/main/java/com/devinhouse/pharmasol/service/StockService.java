package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.StockResponse;
import com.devinhouse.pharmasol.exception.*;
import com.devinhouse.pharmasol.repository.PharmacyRepository;
import com.devinhouse.pharmasol.repository.MedicineRepository;
import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.model.Stock;
import com.devinhouse.pharmasol.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class StockService {
    private static final Logger logger = LogManager.getLogger(StockService.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Transactional
    public void save(Stock stock) {
        stockRepository.save(stock);
        logger.info("Pharmacy's stock saved successfully. CNPJ: {}, Register Number: {}", stock.getCnpj(), stock.getRegisterNumber());
    }

    public Optional<List<StockResponse>> getStockByCnpj(Long cnpj) {
        try {
            List<Stock> stockList = stockRepository.findByCnpj(cnpj);
            List<StockResponse> stockResponses = stockList.stream()
                    .map(StockResponse::new)
                    .collect(Collectors.toList());
            logger.info("Listed all stocks for pharmacy with CNPJ: {}", cnpj);
            return Optional.of(stockResponses);
        } catch (Exception e) {
            logger.error("Error listing stocks. {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<StockResponse> addMedicine(Long cnpj, Integer registerNumber, Integer quantity) {
        try {
            validateInput(cnpj, registerNumber, quantity);

            Optional<Stock> existingStock = stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber);

            if (existingStock.isPresent()) {
                Stock stock = existingStock.get();
                stock.setQuantity(stock.getQuantity() + quantity);
                stock.setUpdateDate(LocalDateTime.now());
                stockRepository.save(stock);
                logger.info("Added {} units of medicine to stock for pharmacy with CNPJ: {}, Register Number: {}", quantity, cnpj, registerNumber);
            } else {
                createNewStock(cnpj, registerNumber, quantity);
            }

            return stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber)
                    .map(StockResponse::new);
        } catch (Exception e) {
            logger.error("Error adding medicine to stock. {}", e.getMessage());
            throw e;
        }
    }

    private void validateInput(Long cnpj, Integer registerNumber, Integer quantity) {
        if (cnpj == null || registerNumber == null || quantity == null) {
            logger.error("Validation failed: All fields are mandatory.");
            throw new ValidationException("All fields are mandatory.");
        }

        if (!pharmacyRepository.existsByCnpj(cnpj)) {
            logger.error("Validation failed: Pharmacy with the specified CNPJ does not exist.");
            throw new PharmacyNotFoundException("Pharmacy with the specified CNPJ does not exist.");
        }

        if (!medicineRepository.existsByRegisterNumber(registerNumber)) {
            logger.error("Validation failed: Medicine with the specified register number does not exist.");
            throw new MedicineNotFoundException("Medicine with the specified register number does not exist.");
        }

        if (quantity <= 0) {
            logger.error("Validation failed: Quantity must be a positive number greater than zero.");
            throw new InvalidQuantityException("Quantity must be a positive number greater than zero.");
        }
    }

    private void createNewStock(Long cnpj, Integer registerNumber, Integer quantity) {
        Stock newStock = new Stock();
        newStock.setCnpj(cnpj);
        newStock.setRegisterNumber(registerNumber);
        newStock.setQuantity(quantity);
        newStock.setUpdateDate(LocalDateTime.now());

        Pharmacy pharmacy = pharmacyRepository.findByCnpj(cnpj);
        if (pharmacy == null) {
            pharmacy = new Pharmacy();
            pharmacy.setCnpj(cnpj);
            pharmacyRepository.save(pharmacy);
        }
        newStock.setPharmacy(pharmacy);

        Medicine medicine = medicineRepository.findByRegisterNumber(registerNumber);
        if (medicine == null) {
            medicine = new Medicine();
            medicine.setRegisterNumber(registerNumber);
            medicineRepository.save(medicine);
        }
        newStock.setMedicine(medicine);

        stockRepository.save(newStock);
        logger.info("Created new stock for pharmacy with CNPJ: {}, Register Number: {}", cnpj, registerNumber);
    }

    @Transactional
    public Optional<StockResponse> sellMedicine(Long cnpj, Integer registerNumber, Integer quantity) {
        try {
            Optional<Stock> existingStock = stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber);

            if (existingStock.isPresent()) {
                Stock stock = existingStock.get();
                int newQuantity = stock.getQuantity() - quantity;

                if (newQuantity < 0) {
                    logger.error("Error selling medicine from stock: Cannot sell more than the available quantity in stock.");
                    throw new OutOfStockException("Cannot sell more than the available quantity in stock.");
                } else {
                    if (newQuantity == 0) {
                        stockRepository.delete(stock);
                        StockResponse responseAfterDeletion = new StockResponse(
                                cnpj,
                                registerNumber,
                                0,
                                LocalDateTime.now()
                        );
                        logger.info("Sold {} units of medicine from stock for pharmacy with CNPJ: {}, Register Number: {}. Stock deleted.", quantity, cnpj, registerNumber);
                        return Optional.of(responseAfterDeletion);
                    } else {
                        stock.setQuantity(newQuantity);
                        stock.setUpdateDate(LocalDateTime.now());
                        stockRepository.save(stock);

                        logger.info("Sold {} units of medicine from stock for pharmacy with CNPJ: {}, Register Number: {}. Updated stock quantity.", quantity, cnpj, registerNumber);
                        return Optional.of(new StockResponse(
                                cnpj,
                                registerNumber,
                                newQuantity,
                                stock.getUpdateDate()
                        ));
                    }
                }
            } else {
                logger.error("Error selling medicine from stock: There's no existing stock for the specified pharmacy and medicine.");
                throw new OutOfStockException("There's no existing stock for the specified pharmacy and medicine.");
            }
        } catch (Exception e) {
            logger.error("Error selling medicine from stock. {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Optional<StockResponse> exchangeMedicine(Long cnpjOrigin, Long cnpjDestiny, Integer registerNumber, Integer quantity) {
        try {
            Optional<Stock> existingStockOrigin = stockRepository.findByCnpjAndRegisterNumber(cnpjOrigin, registerNumber);
            Optional<Stock> existingStockDestiny = stockRepository.findByCnpjAndRegisterNumber(cnpjDestiny, registerNumber);

            if (existingStockOrigin.isPresent()) {
                Stock stockOrigin = existingStockOrigin.get();
                int newQuantityOrigin = stockOrigin.getQuantity() - quantity;

                if (newQuantityOrigin < 0) {
                    logger.error("Error exchanging medicine between pharmacies: Cannot send more than the available quantity in stock.");
                    throw new OutOfStockException("Cannot send more than the available quantity in stock.");
                } else {
                    stockOrigin.setQuantity(newQuantityOrigin);
                    stockOrigin.setUpdateDate(LocalDateTime.now());
                    stockRepository.save(stockOrigin);

                    Stock stockDestiny;
                    int newQuantityDestiny;

                    if (existingStockDestiny.isPresent()) {
                        stockDestiny = existingStockDestiny.get();
                        newQuantityDestiny = stockDestiny.getQuantity() + quantity;
                    } else {
                        stockDestiny = new Stock();
                        stockDestiny.setCnpj(cnpjDestiny);
                        stockDestiny.setRegisterNumber(registerNumber);
                        newQuantityDestiny = quantity;
                    }

                    stockDestiny.setQuantity(newQuantityDestiny);
                    stockDestiny.setUpdateDate(LocalDateTime.now());
                    stockRepository.save(stockDestiny);

                    if (newQuantityOrigin == 0) {
                        stockRepository.delete(stockOrigin);
                    }

                    logger.info("Exchanged {} units of medicine between pharmacies. Origin CNPJ: {}, Destiny CNPJ: {}, Register Number: {}", quantity, cnpjOrigin, cnpjDestiny, registerNumber);
                    return Optional.of(new StockResponse(
                            registerNumber,
                            cnpjOrigin,
                            newQuantityOrigin,
                            cnpjDestiny,
                            newQuantityDestiny
                    ));
                }
            } else {
                logger.error("Error exchanging medicine between pharmacies: There's no existing stock for the specified pharmacy and medicine.");
                throw new OutOfStockException("There's no existing stock for the specified pharmacy and medicine.");
            }
        } catch (Exception e) {
            logger.error("Error exchanging medicine between pharmacies. {}", e.getMessage());
            throw e;
        }
    }
}