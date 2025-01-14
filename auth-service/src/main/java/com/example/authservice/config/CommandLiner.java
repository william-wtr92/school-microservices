package com.example.authservice.config;

import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CommandLiner {
    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("user123").isEmpty()) {
                User user = new User();
                user.setUsername("user123");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole("USER");
                userRepository.save(user);
            }
        };
    }

}
