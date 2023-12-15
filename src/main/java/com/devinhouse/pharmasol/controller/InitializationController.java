package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.model.Medicine;
import com.devinhouse.pharmasol.model.Pharmacy;
import com.devinhouse.pharmasol.model.Stock;
import com.devinhouse.pharmasol.model.enums.MedicineType;
import com.devinhouse.pharmasol.service.MedicineService;
import com.devinhouse.pharmasol.service.PharmacyService;
import com.devinhouse.pharmasol.service.StockService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/initialization")
public class InitializationController {
    private static final Logger logger = LogManager.getLogger(InitializationController.class);

    @Autowired
    public PharmacyService pharmacyService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private StockService stockService;

//  Initial data load: pharmacies, medicines and stocks added
    @Transactional
    @PostMapping
    public ResponseEntity<Void> initializeData(){
        try {
            logger.debug("Initializing data...");

            Pharmacy pharmacy1 = new Pharmacy();
            pharmacy1.setCnpj(90561736000121L);
            pharmacy1.setCompanyName("DevMed Ltda");
            pharmacy1.setTradingName("Farmácia DevMed");
            pharmacy1.setEmail("devmed@farmacia.com");
            pharmacy1.setLandlineCellphone("(44)4444-4444");
            pharmacy1.setCellphone("(44)9444-4441");
            pharmacy1.getAddress().setZipCode(88888999L);
            pharmacy1.getAddress().setStreet("Rua Porto Real");
            pharmacy1.getAddress().setNumber(67);
            pharmacy1.getAddress().setNeighborhood("Westeros");
            pharmacy1.getAddress().setCity("Berlim");
            pharmacy1.getAddress().setState("SC");
            pharmacy1.getAddress().setLatitude(15.23456);
            pharmacy1.getAddress().setLongitude(2.8678687);

            Pharmacy pharmacy2 = new Pharmacy();
            pharmacy2.setCnpj(43178995000198L);
            pharmacy2.setCompanyName("MedHouse Ltda");
            pharmacy2.setTradingName("Farmácia MedHouse");
            pharmacy2.setEmail("medhouse@farmacia.com");
            pharmacy2.setLandlineCellphone("(55)5555-5555");
            pharmacy2.setCellphone("(45)95555-5555");
            pharmacy2.getAddress().setZipCode(8877799L);
            pharmacy2.getAddress().setStreet("Rua Madrid");
            pharmacy2.getAddress().setNumber(76);
            pharmacy2.getAddress().setNeighborhood("Winterfell");
            pharmacy2.getAddress().setCity("Estocolmo");
            pharmacy2.getAddress().setState("SC");
            pharmacy2.getAddress().setLatitude(19.254356);
            pharmacy2.getAddress().setLongitude(3.8987687);

            pharmacyService.save(pharmacy1);
            logger.debug("Pharmacy 1 saved: {}", pharmacy1);
            pharmacyService.save(pharmacy2);
            logger.debug("Pharmacy 2 saved: {}", pharmacy2);

            Medicine medicine1 = new Medicine();
            medicine1.setRegisterNumber(1010);
            medicine1.setName("Programapan");
            medicine1.setLaboratory("Matrix");
            medicine1.setDosage("2x ao dia");
            medicine1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eleifend");
            medicine1.setPrice(111.00F);
            medicine1.setMedicineType(MedicineType.COMMON);

            Medicine medicine2 = new Medicine();
            medicine2.setRegisterNumber(7473);
            medicine2.setName("Cafex");
            medicine2.setLaboratory("Colombia Farm");
            medicine2.setDosage("4x ao dia");
            medicine2.setDescription("Pellentesque non ultricies mauris, ut lobortis elit. Cras nec cursus nibh");
            medicine2.setPrice(51.50F);
            medicine2.setMedicineType(MedicineType.COMMON);

            Medicine medicine3 = new Medicine();
            medicine3.setRegisterNumber(2233);
            medicine3.setName("Estomazol");
            medicine3.setLaboratory("Acme");
            medicine3.setDosage("1x ao dia");
            medicine3.setDescription("Sed risus mauris, consectetur eget egestas vitae");
            medicine3.setPrice(22.50F);
            medicine3.setMedicineType(MedicineType.COMMON);

            Medicine medicine4 = new Medicine();
            medicine4.setRegisterNumber(8880);
            medicine4.setName("Gelox");
            medicine4.setLaboratory("Ice");
            medicine4.setDosage("2x ao dia");
            medicine4.setDescription("Quisque quam orci, vulputate sit amet");
            medicine4.setPrice(11.50F);
            medicine4.setMedicineType(MedicineType.COMMON);

            Medicine medicine5 = new Medicine();
            medicine5.setRegisterNumber(5656);
            medicine5.setName("Aspirazol");
            medicine5.setLaboratory("Acme");
            medicine5.setDosage("3x ao dia");
            medicine5.setDescription("Sed faucibus, libero iaculis pulvinar consequat, augue elit eleifend");
            medicine5.setPrice(10.50F);
            medicine5.setMedicineType(MedicineType.CONTROLLED);

            Medicine medicine6 = new Medicine();
            medicine6.setRegisterNumber(4040);
            medicine6.setName("Propolizol");
            medicine6.setLaboratory("Bee");
            medicine6.setDosage("5x ao dia");
            medicine6.setDescription("Nunc euismod ipsum purus, sit amet finibus libero ultricies in");
            medicine6.setPrice(10.50F);
            medicine6.setMedicineType(MedicineType.CONTROLLED);

            medicineService.save(medicine1);
            logger.debug("Medicine 1 saved: {}", medicine1);
            medicineService.save(medicine2);
            logger.debug("Medicine 2 saved: {}", medicine2);
            medicineService.save(medicine3);
            logger.debug("Medicine 3 saved: {}", medicine3);
            medicineService.save(medicine4);
            logger.debug("Medicine 4 saved: {}", medicine4);
            medicineService.save(medicine5);
            logger.debug("Medicine 5 saved: {}", medicine5);
            medicineService.save(medicine6);
            logger.debug("Medicine 6 saved: {}", medicine6);

            Stock stock1 = new Stock();
            stock1.setCnpj(90561736000121L);
            stock1.setRegisterNumber(1010);
            stock1.setQuantity(12);
            stock1.setUpdateDate(LocalDateTime.now());

            Stock stock2 = new Stock();
            stock2.setCnpj(90561736000121L);
            stock2.setRegisterNumber(7473);
            stock2.setQuantity(10);
            stock2.setUpdateDate(LocalDateTime.now());

            Stock stock3 = new Stock();
            stock3.setCnpj(43178995000198L);
            stock3.setRegisterNumber(7473);
            stock3.setQuantity(2);
            stock3.setUpdateDate(LocalDateTime.now());

            Stock stock4 = new Stock();
            stock4.setCnpj(43178995000198L);
            stock4.setRegisterNumber(2233);
            stock4.setQuantity(15);
            stock4.setUpdateDate(LocalDateTime.now());

            Stock stock5 = new Stock();
            stock5.setCnpj(43178995000198L);
            stock5.setRegisterNumber(8880);
            stock5.setQuantity(16);
            stock5.setUpdateDate(LocalDateTime.now());

            Stock stock6 = new Stock();
            stock6.setCnpj(43178995000198L);
            stock6.setRegisterNumber(4040);
            stock6.setQuantity(22);
            stock6.setUpdateDate(LocalDateTime.now());

            stockService.save(stock1);
            logger.debug("Stock 1 saved: {}", stock1);
            stockService.save(stock2);
            logger.debug("Stock 2 saved: {}", stock2);
            stockService.save(stock3);
            logger.debug("Stock 3 saved: {}", stock3);
            stockService.save(stock4);
            logger.debug("Stock 4 saved: {}", stock4);
            stockService.save(stock5);
            logger.debug("Stock 5 saved: {}", stock5);
            stockService.save(stock6);
            logger.debug("Stock 6 saved: {}", stock6);

            logger.info("Data initialization completed successfully");
            return ResponseEntity.ok().build();
    } catch (Exception e) {
        logger.error("Error initializing data", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}
