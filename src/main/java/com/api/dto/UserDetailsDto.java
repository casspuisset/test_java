package com.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDetailsDto {
    private Integer id;

    private String name;

    private String mail;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
