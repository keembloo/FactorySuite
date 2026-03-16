package com.factorysuite.controller;

import com.factorysuite.service.Userservice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import com.factorysuite.util.JwtUtil;

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
        System.out.println("컨트롤러username"+username);
        System.out.println("컨트롤러password"+password);
        Map<String, String> res = new HashMap<>();

        if (valid) {

            String token = JwtUtil.createToken(username);
            res.put("token", token);

        } else {

            res.put("error", "Invalid credentials");
        }

        return res;
    }
}