package com.repair.util;

public class ValidationException extends Exception {

    private String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidationException: " + message;
    }
}
