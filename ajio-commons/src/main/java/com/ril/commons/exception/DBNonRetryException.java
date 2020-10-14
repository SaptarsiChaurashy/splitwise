package com.ril.commons.exception;

public class DBNonRetryException extends RuntimeException {
    private final String message;

    public DBNonRetryException(String message) {
        this.message = message;
    }
}
