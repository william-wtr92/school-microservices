package com.example.authservice.service.impl;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtTokenProvider;
import com.example.authservice.service.AuthService;
import com.example.authservice.utils.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserMapper userMapper;

    @Override
    public String login(LoginDto loginDto) {
        String username = loginDto.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                loginDto.getPassword()
        );

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public void register(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ApiException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String validateToken(String token) {
        if(!jwtTokenProvider.validateToken(token)){
            throw new ApiException("Invalid token", HttpStatus.FORBIDDEN);
        }

        return jwtTokenProvider.getUsername(token);
    }
}
