package com.api.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String tokenType = "bearer ";

    public AuthResponseDto(String token) {
        this.token = token;
    }
}
