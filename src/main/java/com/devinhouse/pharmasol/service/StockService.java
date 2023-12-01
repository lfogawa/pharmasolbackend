package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.dtos.StockResponse;
import com.devinhouse.pharmasol.repository.PharmacyRepository;
import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.model.Stock;
import com.devinhouse.pharmasol.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
