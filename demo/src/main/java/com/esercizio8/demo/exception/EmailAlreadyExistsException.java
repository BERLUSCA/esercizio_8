package com.esercizio8.demo.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message) { super(message); }
}
