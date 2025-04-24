package io.sleepyhoon.project1.exception;

public class CoffeeDuplicationException extends RuntimeException {
    public CoffeeDuplicationException(String message) {
        super(
                "A coffee with " + message + " already exists.");
    }
}
