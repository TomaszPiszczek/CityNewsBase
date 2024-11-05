package com.example.CityNewsBase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
}
