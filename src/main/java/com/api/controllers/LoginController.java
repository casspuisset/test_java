package com.api.controllers;

import com.api.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.AuthResponseDto;
import com.api.dto.LoginRequestDto;
import com.api.dto.RegisterRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        var authResponseDto = userService.login(loginRequestDto);

        return ResponseEntity.ok().body(authResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody RegisterRequestDto user) {

        var authResponseDto = userService.register(user);
        return ResponseEntity.ok().body(authResponseDto);

    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserDetails(@RequestParam Authentication authentication) {
        return userService.getUserDetails(authentication);
    }
}