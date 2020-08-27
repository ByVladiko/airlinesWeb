package com.airlines.exception;

public class ExceptionHandlingController extends RuntimeException {

    public ExceptionHandlingController(String message) {
        super(message);
    }

    public ExceptionHandlingController(String message, Throwable cause) {
        super(message, cause);
    }
}
