package com.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalDto {
    private Integer id;

    private String name;

    private Double surface;

    private Double price;

    private String picture;

    private String description;

    private Integer owner_id;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
