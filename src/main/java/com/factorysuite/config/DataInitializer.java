package com.factorysuite.config;

import com.factorysuite.domain.User;
import com.factorysuite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers() {
        return args -> {

            String username = "admin";

            if (userRepository.findByUsername(username).isEmpty()) {

                User admin = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode("1234"))
                        .build();

                userRepository.save(admin);

                System.out.println("기본 관리자 계정 생성: admin / 1234");
            }
        };
    }
}