package com.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RentalsRequestDto {
    private String name;

    private Double surface;

    private Double price;

    private String picture;

    private String description;

    private Long ownerId;
}
