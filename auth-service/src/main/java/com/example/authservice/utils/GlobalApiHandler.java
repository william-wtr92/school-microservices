package com.example.authservice.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalApiHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(), ex.getStatus().value(), false);
        return new ResponseEntity<>(apiResponse, ex.getStatus());
    }

}
