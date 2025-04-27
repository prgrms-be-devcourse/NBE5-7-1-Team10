package io.sleepyhoon.project1.exception;

public class OrderOwnerMismatchException extends RuntimeException {
    public OrderOwnerMismatchException(String message) {
        super(message);
    }
}
