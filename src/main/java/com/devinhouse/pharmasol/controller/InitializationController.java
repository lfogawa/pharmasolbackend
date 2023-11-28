package com.devinhouse.pharmasol.controller;

import com.devinhouse.pharmasol.model.Pharmacy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/initialization")
public class InitializationController {
    @Autowired
    public PharmacyService pharmacyService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private StockService stockService;

    @Transactional
    @PostMapping
    public ResponseEntity<Void> initializeData(){
        Pharmacy pharmacy1 = new Pharmacy();
        pharmacy1.setCnpj("90561736000121");
        pharmacy1.setRazaoSocial("DevMed Ltda");
        pharmacy1.setNomeFantasia("Farmácia DevMed");
        pharmacy1.setEmail("devmed@farmacia.com");
        pharmacy1.setTelefone("(44)4444-4444");
        pharmacy1.setCelular("(44)9444-4441");
        pharmacy1.setCep("88888999");
        pharmacy1.setLogradouro("Rua Porto Real");
        pharmacy1.setNumero("67");
        pharmacy1.setBairro("Westeros");
        pharmacy1.setCidade("Berlim");
        pharmacy1.setEstado("SC");
        pharmacy1.setLatitude(15.23456);
        pharmacy1.setLongitude(2.8678687);

        Pharmacy pharmacy2 = new Pharmacy();
        pharmacy2.setCnpj("43178995000198");
        pharmacy2.setRazaoSocial("MedHouse Ltda");
        pharmacy2.setNomeFantasia("Farmácia MedHouse");
        pharmacy2.setEmail("medhouse@farmacia.com");
        pharmacy2.setTelefone("(55)5555-5555");
        pharmacy2.setCelular("(45)95555-5555");
        pharmacy2.setCep("8877799");
        pharmacy2.setLogradouro("Rua Madrid");
        pharmacy2.setNumero("76");
        pharmacy2.setBairro("Winterfell");
        pharmacy2.setCidade("Estocolmo");
        pharmacy2.setEstado("SC");
        pharmacy2.setLatitude(19.254356);
        pharmacy2.setLongitude(3.8987687);

        pharmacyService.save(pharmacy1);
        pharmacyService.save(pharmacy2);

        Medicine medicine1 = new Medicine();
        medicine1.setNroRegistro("1010");
        medicine1.setNome("Programapan");
        medicine1.setLaboratorio("Matrix");
        medicine1.setDosagem("2x ao dia");
        medicine1.setDescricao("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eleifend");
        medicine1.setPreco(111.00);
        medicine1.setTipo(TipoMedicamento.COMMON);

        Medicine medicine2 = new Medicine();
        medicine2.setNroRegistro("7473");
        medicine2.setNome("Cafex");
        medicine2.setLaboratorio("Colombia Farm");
        medicine2.setDosagem("4x ao dia");
        medicine2.setDescricao("Pellentesque non ultricies mauris, ut lobortis elit. Cras nec cursus nibh");
        medicine2.setPreco(51.50);
        medicine2.setTipo(TipoMedicamento.COMMON);

        Medicine medicine3 = new Medicine();
        medicine3.setNroRegistro("2233");
        medicine3.setNome("Estomazol");
        medicine3.setLaboratorio("Acme");
        medicine3.setDosagem("1x ao dia");
        medicine3.setDescricao("Sed risus mauris, consectetur eget egestas vitae");
        medicine3.setPreco(22.50);
        medicine3.setTipo(TipoMedicamento.COMMON);

        Medicine medicine4 = new Medicine();
        medicine4.setNroRegistro("8880");
        medicine4.setNome("Gelox");
        medicine4.setLaboratorio("Ice");
        medicine4.setDosagem("2x ao dia");
        medicine4.setDescricao("Quisque quam orci, vulputate sit amet");
        medicine4.setPreco(11.50);
        medicine4.setTipo(TipoMedicamento.COMMON);

        Medicine medicine5 = new Medicine();
        medicine5.setNroRegistro("5656");
        medicine5.setNome("Aspirazol");
        medicine5.setLaboratorio("Acme");
        medicine5.setDosagem("3x ao dia");
        medicine5.setDescricao("Sed faucibus, libero iaculis pulvinar consequat, augue elit eleifend");
        medicine5.setPreco(10.50);
        medicine5.setTipo(TipoMedicamento.CONTROLLED);

        Medicine medicine6 = new Medicine();
        medicine6.setNroRegistro("4040");
        medicine6.setNome("Propolizol");
        medicine6.setLaboratorio("Bee");
        medicine6.setDosagem("5x ao dia");
        medicine6.setDescricao("Nunc euismod ipsum purus, sit amet finibus libero ultricies in");
        medicine6.setPreco(10.50);
        medicine6.setTipo(TipoMedicamento.CONTROLLED);

        medicineService.save(medicine1);
        medicineService.save(medicine2);
        medicineService.save(medicine3);
        medicineService.save(medicine4);
        medicineService.save(medicine5);
        medicineService.save(medicine6);

        Stock stock1 = new Stock();
        stock1.setCnpj("90561736000121");
        stock1.setNroRegistro("1010");
        stock1.setQuantidade(12);
        stock1.setDataAtualizacao(LocalDateTime.now());

        Stock stock2 = new Stock();
        stock2.setCnpj("90561736000121");
        stock2.setNroRegistro("7473");
        stock2.setQuantidade(10);
        stock2.setDataAtualizacao(LocalDateTime.now());

        Stock stock3 = new Stock();
        stock3.setCnpj("43178995000198");
        stock3.setNroRegistro("7473");
        stock3.setQuantidade(2);
        stock3.setDataAtualizacao(LocalDateTime.now());

        Stock stock4 = new Stock();
        stock4.setCnpj("43178995000198");
        stock4.setNroRegistro("2233");
        stock4.setQuantidade(15);
        stock4.setDataAtualizacao(LocalDateTime.now());

        Stock stock5 = new Stock();
        stock5.setCnpj("43178995000198");
        stock5.setNroRegistro("8880");
        stock5.setQuantidade(16);
        stock5.setDataAtualizacao(LocalDateTime.now());

        Stock stock6 = new Stock();
        stock6.setCnpj("43178995000198");
        stock6.setNroRegistro("4040");
        stock6.setQuantidade(22);
        stock6.setDataAtualizacao(LocalDateTime.now());

        // Save the Stock objects in the database
        stockService.save(stock1);
        stockService.save(stock2);
        stockService.save(stock3);
        stockService.save(stock4);
        stockService.save(stock5);
        stockService.save(stock6);

        // Return a response with HTTP status 200 OK
        return ResponseEntity.ok().build();



        return ResponseEntity.ok().build();
    }


}
