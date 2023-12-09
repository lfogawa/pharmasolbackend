package com.devinhouse.pharmasol.exception;

public class PharmacyNotFoundException extends RuntimeException{
    public PharmacyNotFoundException(String message) {
        super(message);
    }
}