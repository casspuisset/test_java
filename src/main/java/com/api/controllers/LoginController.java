package com.api.controllers;

import com.api.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.LoginRequestDto;
import com.api.dto.RegisterRequestDto;
import com.api.dto.UserDetailsDto;

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
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        var authResponseDto = userService.login(loginRequestDto);

        return ResponseEntity.ok().body(authResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto user) {

        var authResponseDto = userService.register(user);
        return ResponseEntity.ok().body(authResponseDto);

    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDto> getUserDetails(@RequestParam Authentication authentication) {
        var response = userService.getUserDetails(authentication);
        return ResponseEntity.ok().body(response);
    }
}