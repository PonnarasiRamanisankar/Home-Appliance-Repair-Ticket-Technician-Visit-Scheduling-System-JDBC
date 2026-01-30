package com.repair.util;

public class TechnicianNotSuitableException extends Exception {

    private String message;

    public TechnicianNotSuitableException(String message) {
        super(message);   // keep message in the base Exception as well
        this.message = message;
    }

    @Override
    public String toString() {
        return "Technician Not Suitable: " + message;
    }
}
