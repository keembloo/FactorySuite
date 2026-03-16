package com.factorysuite.service;

import com.factorysuite.domain.User;
import com.factorysuite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Userservice {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 암호화

    public User register(String username, String rawPassword) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .build();
        return userRepository.save(user);
    }

    public boolean validate(String username, String rawPassword) {
        System.out.println("서비스>>>>유저네임"+username);
        System.out.println("서비스>>>>유저비번"+rawPassword);
        return userRepository.findByUsername(username)
                .map(user -> {
                    System.out.println("DB 패ㅑ스워드>>>>"+user.getPassword());
                    boolean matchresult =  passwordEncoder.matches(rawPassword, user.getPassword());
                    System.out.println("결과값>>>>"+matchresult);
                  return matchresult;
                })
                .orElse(false);
    }
}