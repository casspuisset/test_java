package com.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.UserDetailsDto;
import com.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Get a user by their ID", responses = {
            @ApiResponse(description = "Successfully retrieved the user by their ID", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"id\":2,\"name\":\"Owner Name\",\"email\":\"test@test.com\",\"created_at\":\"2022/02/02\",\"updated_at\":\"2022/08/02\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)
    }, security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable final Integer id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }
}
