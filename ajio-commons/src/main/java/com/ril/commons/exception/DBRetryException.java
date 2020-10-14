package com.ril.commons.exception;

public class DBRetryException extends RuntimeException {
    private final String message;
    public DBRetryException(String message) {
        this.message = message;
    }
}
