package com.factorysuite.controller;

import com.factorysuite.service.Userservice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Userservice userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        boolean valid = userService.validate(username, password);
        Map<String, String> res = new HashMap<>();
        if (valid) {
            // 실제 프로젝트에서는 JWT 토큰 발급
            res.put("token", "dummy-jwt-token");
        } else {
            res.put("error", "Invalid credentials");
        }
        return res;
    }
}