package com.esercizio8.demo.Exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message) { super(message); }
}
