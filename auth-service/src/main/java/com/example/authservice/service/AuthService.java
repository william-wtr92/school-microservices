package com.example.authservice.service;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;

public interface AuthService {
    String login(LoginDto loginDto);
    void register(UserDto userDto);
    String validateToken(String token);
}
