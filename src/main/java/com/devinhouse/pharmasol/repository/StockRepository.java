package com.devinhouse.pharmasol.repository;

import com.devinhouse.pharmasol.model.IdStock;
import com.devinhouse.pharmasol.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, IdStock> {
    List<Stock> findByCnpj(Long cnpj);
}
