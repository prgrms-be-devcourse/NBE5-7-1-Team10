package io.sleepyhoon.project1.exception;

public class CoffeeInvalidRequestException extends RuntimeException {
    public CoffeeInvalidRequestException() {
        super("Cannot create coffee with a null or empty name.");
    }
}
