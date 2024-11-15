package com.example.shop.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;

@Configuration
public class ApplicationInitConfig {
    
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin") == null) {

                User user = User.builder()
                        .username("admin")
                        .password("admin")
                        .build();
                userRepository.save(user);
            }
        };
    }
}

