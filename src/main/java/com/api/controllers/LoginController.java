package com.api.controllers;

import com.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.RegisterRequestDto;
import com.api.service.JWTService;

@RestController
public class LoginController {

    private final UserService userService;

    private JWTService jwtService;

    public LoginController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String getToken(Authentication authentication) {

        String token = jwtService.generateToken(authentication);

        return token;

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}