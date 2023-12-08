package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.StockResponse;
import com.devinhouse.pharmasol.exception.ValidationException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private MedicineRepository medicineRepository;

    @Transactional
    public void save(Stock stock){
        stockRepository.save(stock);
    }

    public Optional<List<StockResponse>> getStockByCnpj(Long cnpj) {
        try {
            List<Stock> stockList = stockRepository.findByCnpj(cnpj);
            List<StockResponse> stockResponses = stockList.stream()
                    .map(StockResponse::new)
                    .collect(Collectors.toList());
            return Optional.of(stockResponses);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<StockResponse> addMedicine(Long cnpj, Integer registerNumber, Integer quantity){
        Optional<Stock> existingStock = stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber);

        if(existingStock.isPresent()){
            Stock stock = existingStock.get();
            stock.setQuantity(stock.getQuantity() + quantity);
            stock.setUpdateDate(LocalDateTime.now());
            stockRepository.save(stock);
        } else {
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
        }

        return stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber)
                .map(StockResponse::new);
    }

    @Transactional
    public Optional<StockResponse> sellMedicine(Long cnpj, Integer registerNumber, Integer quantity) {
        Optional<Stock> existingStock = stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber);

        if (existingStock.isPresent()) {
            Stock stock = existingStock.get();
            int newQuantity = stock.getQuantity() - quantity;

            if (newQuantity < 0) {
                throw new ValidationException("Cannot sell more than the available quantity in stock.");
            } else {
                stock.setQuantity(newQuantity);
                stock.setUpdateDate(LocalDateTime.now());
                stockRepository.save(stock);

                if (newQuantity == 0) {
                    stockRepository.delete(stock);
                    return Optional.of(new StockResponse(stock));
                }
            }
        } else {
            throw new ValidationException("There's no existing stock for the specified pharmacy and medicine.");
        }

        return stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber)
                .map(StockResponse::new);
    }

}
