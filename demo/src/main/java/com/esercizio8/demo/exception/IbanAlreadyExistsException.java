package com.esercizio8.demo.exception;

public class IbanAlreadyExistsException extends RuntimeException{
    public IbanAlreadyExistsException(String message) {
        super(message);
    }
}
