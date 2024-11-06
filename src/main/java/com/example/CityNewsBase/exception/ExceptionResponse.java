package com.example.CityNewsBase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private final String status;
    private final String message;
    private final Map<String, String> validationErrors;

    public ExceptionResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.validationErrors = null;
    }
}
