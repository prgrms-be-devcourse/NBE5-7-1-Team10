package io.sleepyhoon.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private ErrorDetail error;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponseDto(String code, String message, int status) {
        this.error = new ErrorDetail(code, message);
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    @Data
    @AllArgsConstructor
    public static class ErrorDetail {
        private String code;
        private String message;
    }
}