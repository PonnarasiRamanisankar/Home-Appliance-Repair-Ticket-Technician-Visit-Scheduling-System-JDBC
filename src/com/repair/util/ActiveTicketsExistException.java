package com.repair.util;

public class ActiveTicketsExistException extends Exception {

    private String message;

    public ActiveTicketsExistException(String message) {
        super(message);      // keep message in base Exception
        this.message = message;
    }

    @Override
    public String toString() {
        return "Active Tickets Exist: " + message;
    }
}
