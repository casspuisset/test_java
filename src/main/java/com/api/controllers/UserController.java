package com.api.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.model.DBUser;
import com.api.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {

    }

    @GetMapping("/users/{id}")
    public Optional<DBUser> getUserById(final Long id) {
        return userService.getUserById(id);
    }
}
