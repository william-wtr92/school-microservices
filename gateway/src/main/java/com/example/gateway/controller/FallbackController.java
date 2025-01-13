package com.example.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/school")
    public ResponseEntity<String> schoolFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("School Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/fallback/student")
    public ResponseEntity<String> studentFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Student Service is currently unavailable. Please try again later.");
    }
}
