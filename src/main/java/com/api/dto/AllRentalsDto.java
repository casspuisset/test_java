package com.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class AllRentalsDto {
    private List<RentalDto> rentals;
}
