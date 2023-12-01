package com.devinhouse.pharmasol.model;

import com.devinhouse.pharmasol.dtos.PharmacyRequest;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "PHARMACY")
public class Pharmacy {
    @Id
    @Column(nullable = false)
    private Long cnpj;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String tradingName;
    @Column(nullable = false)
    private String email;
    private String landlineCellphone;
    @Column(nullable = false)
    private String cellphone;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "pharmacy")
    private List<Stock> stocks;

    @ManyToMany
    @JoinTable(name = "PHARMACY_MEDICINE",
            joinColumns = @JoinColumn(name = "cnpj"),
            inverseJoinColumns = @JoinColumn(name = "registerNumber"))
    private List<Medicine> medicines;

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Pharmacy() {
        this.address = new Address();
    }

    public Pharmacy(Long cnpj, String companyName, String tradingName, String email, String landlineCellphone, String cellphone, Address address) {
        this.cnpj = cnpj;
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.email = email;
        this.landlineCellphone = landlineCellphone;
        this.cellphone = cellphone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "cnpj=" + cnpj +
                ", companyName='" + companyName + '\'' +
                ", tradingName='" + tradingName + '\'' +
                ", email='" + email + '\'' +
                ", landlineCellphone='" + landlineCellphone + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", address=" + address +
                '}';
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLandlineCellphone() {
        return landlineCellphone;
    }

    public void setLandlineCellphone(String landlineCellphone) {
        this.landlineCellphone = landlineCellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
