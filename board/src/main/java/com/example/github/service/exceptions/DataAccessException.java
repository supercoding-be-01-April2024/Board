package com.example.github.service.exceptions;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String message) {
        super(message);
    }
}
