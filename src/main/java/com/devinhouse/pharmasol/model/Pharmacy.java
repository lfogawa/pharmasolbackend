package com.devinhouse.pharmasol.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PHARMACY")
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long cnpj;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String tradingName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String landlineCellphone;
    private String cellphone;
    @Embedded
    private Address address;

    public Pharmacy() {
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
