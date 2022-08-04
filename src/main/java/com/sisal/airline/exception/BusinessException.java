package com.sisal.airline.exception;


public class BusinessException extends RuntimeException {

    public BusinessException(String error) {
        super(error);
    }
}