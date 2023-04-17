package com.esercizio8.demo.Exception;

public class IbanAlreadyExistsException extends RuntimeException{
    public IbanAlreadyExistsException(String message) {
        super(message);
    }
}
