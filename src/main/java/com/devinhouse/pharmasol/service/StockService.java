package com.devinhouse.pharmasol.service;

import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public void save(Stock stock){
        stockRepository.save(stock);
    }
}
