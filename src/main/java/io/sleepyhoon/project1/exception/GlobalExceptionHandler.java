package io.sleepyhoon.project1.exception;

import io.sleepyhoon.project1.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCoffeeNotFound(CoffeeNotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto("COFFEE_NOT_FOUND", e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CoffeeInvalidRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleCoffeeInvalidRequestException(CoffeeInvalidRequestException e) {
        ErrorResponseDto response = new ErrorResponseDto("COFFEE_INVALID_REQUEST", e.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
   
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CoffeeDuplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleCoffeeDuplication(CoffeeDuplicationException e) {
        ErrorResponseDto response = new ErrorResponseDto("COFFEE_DUPLICATION", e.getMessage(), 409);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFound(OrderNotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto("ORDER_NOT_FOUND", e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}