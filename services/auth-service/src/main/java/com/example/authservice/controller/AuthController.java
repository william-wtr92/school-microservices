package com.example.authservice.controller;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.service.AuthService;
import com.example.authservice.utils.ApiResponse;
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
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "jwt=" + token + "; HttpOnly; SameSite=Strict; Path=/; Max-Age=86400");

        ApiResponse<?> response = new ApiResponse<>("Login successful", HttpStatus.OK.value(), true);

        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody UserDto userDto) {
        authService.register(userDto);

        ApiResponse<?> response = new ApiResponse<>("User registered successfully", HttpStatus.OK.value(), true);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken(@CookieValue(value = "jwt", required = false) String token) {
        if (token == null || token.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>(
                    "Token is invalid",
                    HttpStatus.UNAUTHORIZED.value(),
                    null,
                    false
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String username = authService.validateToken(token);

        ApiResponse<String> response = new ApiResponse<>(
                "Token is valid",
                HttpStatus.OK.value(),
                username,
                true
        );

        return ResponseEntity.ok().body(response);
    }
}
