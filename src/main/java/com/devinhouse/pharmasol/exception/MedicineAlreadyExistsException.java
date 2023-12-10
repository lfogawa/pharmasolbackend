package com.devinhouse.pharmasol.exception;

public class MedicineAlreadyExistsException extends RuntimeException {
    public MedicineAlreadyExistsException(String message) {
        super(message);
    }
}