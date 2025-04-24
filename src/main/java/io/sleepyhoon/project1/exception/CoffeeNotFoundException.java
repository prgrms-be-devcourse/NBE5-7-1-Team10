package io.sleepyhoon.project1.exception;

import io.sleepyhoon.project1.entity.Coffee;

public class CoffeeNotFoundException extends RuntimeException {
    public CoffeeNotFoundException(Long id) {
        super("Coffee with id " + id + " not found");
    }

    public CoffeeNotFoundException() {
        super("The menu is empty! Should add some coffee");
    }
}
