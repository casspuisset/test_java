package com.api.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.RentalsRequestDto;
import com.api.model.Rental;
import com.api.service.RentalService;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalsService;

    @GetMapping("/rentals")
    public Iterable<Rental> getAllRentals() {
        return rentalsService.getAllRentals();
    }

    @GetMapping("/rentals/:id")
    public Optional<Rental> getRental(final Long id) {
        return rentalsService.getRental(id);
    }

    @PostMapping("rentals")
    public Rental postRentals(RentalsRequestDto rentals) {
        return rentalsService.postRentals(rentals);
    }

    // @PutMapping("rentals/:id")

}