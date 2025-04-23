package io.sleepyhoon.project1.exception;

import io.sleepyhoon.project1.dto.ErrorResponseDto;
import io.sleepyhoon.project1.exception.CoffeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//예외를 json으로 보내준다.
@ResponseStatus(HttpStatus.NOT_FOUND)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CoffeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCoffeeNotFound(CoffeeNotFoundException e) {
        ErrorResponseDto response = new ErrorResponseDto("COFFEE_NOT_FOUND", e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);


    }
}