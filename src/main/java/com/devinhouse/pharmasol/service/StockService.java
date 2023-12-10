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

//  Method to save pharmacies stocks when post mapping (initialization controller)
    @Transactional
    public void save(Stock stock){
        stockRepository.save(stock);
    }

//  Method to list all pharmacies stocks when get mapping (stock controller)
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

//  Method to add medicine in a pharmacy's stock when post mapping (stock controller)
    @Transactional
    public Optional<StockResponse> addMedicine(Long cnpj, Integer registerNumber, Integer quantity) {
//      Validate if obligatory fields are filled
        validateInput(cnpj, registerNumber, quantity);

        Optional<Stock> existingStock = stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber);

        if (existingStock.isPresent()) {
            Stock stock = existingStock.get();
            stock.setQuantity(stock.getQuantity() + quantity);
            stock.setUpdateDate(LocalDateTime.now());
            stockRepository.save(stock);
        }
//      If pharmacy's stock isn't present, create
        else {
            createNewStock(cnpj, registerNumber, quantity);
        }

        return stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber)
                .map(StockResponse::new);
    }

//  Method to validate if obligatory fields are filled, if pharmacy or medicine exists and if quantity is greater than zero
    private void validateInput(Long cnpj, Integer registerNumber, Integer quantity) {
        if (cnpj == null || registerNumber == null || quantity == null) {
            throw new ValidationException("All fields are mandatory.");
        }

        if (!pharmacyRepository.existsByCnpj(cnpj)) {
            throw new PharmacyNotFoundException("Pharmacy with the specified CNPJ does not exist.");
        }

        if (!medicineRepository.existsByRegisterNumber(registerNumber)) {
            throw new MedicineNotFoundException("Medicine with the specified register number does not exist.");
        }

        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be a positive number greater than zero.");
        }
    }

//  Method to create a new pharmacy's stock if it isn't present
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
    }

//  Method to sell medicine in a pharmacy's stock when delete mapping (stock controller)
    @Transactional
    public Optional<StockResponse> sellMedicine(Long cnpj, Integer registerNumber, Integer quantity) throws OutOfStockException {
        Optional<Stock> existingStock = stockRepository.findByCnpjAndRegisterNumber(cnpj, registerNumber);

        if (existingStock.isPresent()) {
            Stock stock = existingStock.get();
            int newQuantity = stock.getQuantity() - quantity;

            if (newQuantity < 0) {
                throw new OutOfStockException("Cannot sell more than the available quantity in stock.");
            } else {
                if (newQuantity == 0) {
//                  If pharmacy's stock reaches zero, it's deleted and a temporary response is shown
                    stockRepository.delete(stock);
                    StockResponse responseAfterDeletion = new StockResponse(
                            cnpj,
                            registerNumber,
                            0,
                            LocalDateTime.now()
                    );
                    return Optional.of(responseAfterDeletion);
                }
//              If pharmacy's stock doesn't reach zero, medicine quantity and updateDate are updated
                else {
                    stock.setQuantity(newQuantity);
                    stock.setUpdateDate(LocalDateTime.now());
                    stockRepository.save(stock);

                    return Optional.of(new StockResponse(
                            cnpj,
                            registerNumber,
                            newQuantity,
                            stock.getUpdateDate()
                    ));
                }
            }
        }
//      If pharmacy's stock isn't present, an error is thrown
        else {
            throw new OutOfStockException("There's no existing stock for the specified pharmacy and medicine.");
        }
    }

//  Method to exchange medicine between two pharmacies stocks when put mapping (stock controller)
    @Transactional
    public Optional<StockResponse> exchangeMedicine(Long cnpjOrigin, Long cnpjDestiny, Integer registerNumber, Integer quantity) {
        Optional<Stock> existingStockOrigin = stockRepository.findByCnpjAndRegisterNumber(cnpjOrigin, registerNumber);
        Optional<Stock> existingStockDestiny = stockRepository.findByCnpjAndRegisterNumber(cnpjDestiny, registerNumber);

        if (existingStockOrigin.isPresent()) {
            Stock stockOrigin = existingStockOrigin.get();
            int newQuantityOrigin = stockOrigin.getQuantity() - quantity;

//          If medicine quantity in pharmacy's stock origin is negative, an error is thrown
            if (newQuantityOrigin < 0) {
                throw new OutOfStockException("Cannot send more than the available quantity in stock.");
            }
//          If medicine quantity in pharmacy's stock origin is not negative or reaches zero, medicine quantity and updateDate are updated
            else {
                stockOrigin.setQuantity(newQuantityOrigin);
                stockOrigin.setUpdateDate(LocalDateTime.now());
                stockRepository.save(stockOrigin);

                Stock stockDestiny;
                int newQuantityDestiny;

//              If pharmacy's stock destiny is present, add medicine quantity exchanged from pharmacy's stock origin
                if (existingStockDestiny.isPresent()) {
                    stockDestiny = existingStockDestiny.get();
                    newQuantityDestiny = stockDestiny.getQuantity() + quantity;
                }
//              If pharmacy's stock destiny is not present, create
                else {
                    stockDestiny = new Stock();
                    stockDestiny.setCnpj(cnpjDestiny);
                    stockDestiny.setRegisterNumber(registerNumber);
                    newQuantityDestiny = quantity;
                }

//              Medicine quantity and updateDate from pharmacy's stock destiny are updated
                stockDestiny.setQuantity(newQuantityDestiny);
                stockDestiny.setUpdateDate(LocalDateTime.now());
                stockRepository.save(stockDestiny);

//              If medicine quantity in pharmacy's stock origin reaches zero, it's deleted
                if (newQuantityOrigin == 0) {
                    stockRepository.delete(stockOrigin);
                }

//              If exchange medicine method is successful, a response is shown
                return Optional.of(new StockResponse(
                        registerNumber,
                        cnpjOrigin,
                        newQuantityOrigin,
                        cnpjDestiny,
                        newQuantityDestiny
                ));
            }
        }
//     If pharmacy's stock origin isn't presente, an error is shown
        else {
            throw new OutOfStockException("There's no existing stock for the specified pharmacy and medicine.");
        }
    }
}
