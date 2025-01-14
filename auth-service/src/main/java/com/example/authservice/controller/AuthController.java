package com.example.authservice.controller;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "jwt=" + token + "; HttpOnly; SameSite=Strict; Path=/; Max-Age=86400");

        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        authService.register(userDto);

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@CookieValue(value = "jwt", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }

        String username = authService.validateToken(token);

        return ResponseEntity.ok("Token is valid. Username: " + username);
    }
}
