package com.example.CityNewsBase.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ExceptionResponse> handleWebClientResponseException(WebClientResponseException ex) {
        if (ex.getStatusCode() == HttpStatus.PAYMENT_REQUIRED) {
            ExceptionResponse response = new ExceptionResponse("402 Payment Required",
                    "Payment is required to access this resource.");
            return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);
        }

        ExceptionResponse response = new ExceptionResponse("Error",
                "An error occurred while calling an external service.");
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(fieldName, message);
        }

        ExceptionResponse response = new ExceptionResponse("Validation Error",
                "Input data validation failed", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException ex) {
        ExceptionResponse response = new ExceptionResponse("Null Pointer Exception",
                "A required object was null. Please check your input data or method calls.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
