package com.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.RentalRequestDto;
import com.api.dto.RentalResponseDto;
import com.api.dto.RentalUpdateRequestDto;
import com.api.model.Rental;
import com.api.service.RentalService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalsService;

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRental(@PathVariable final Integer id) {
        try {
            Rental rental = rentalsService.getRental(id);
            return ResponseEntity.ok().body(rental);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Rental>> getAllRentals() {
        var rentalsResponseDto = rentalsService.getAllRentals();
        return ResponseEntity.ok().body(rentalsResponseDto);
    }

    @PostMapping(path = "/**", consumes = "multipart/form-data")
    public ResponseEntity<RentalResponseDto> createRentals(@ModelAttribute RentalRequestDto rentalRequestDto) {
        // en attendant

        RentalResponseDto rentalResponseDto = rentalsService.createRentals(rentalRequestDto);
        return ResponseEntity.ok().body(rentalResponseDto);
    }

    @PutMapping(path = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<RentalResponseDto> updateRentals(@PathVariable String id,
            @ModelAttribute RentalRequestDto rentalRequestDto) {

        RentalUpdateRequestDto newRequestDto = rentalsService.createUpdateDto(rentalRequestDto, id);
        RentalResponseDto rentalResponseDto = rentalsService.updateRentals(newRequestDto);
        return ResponseEntity.ok().body(rentalResponseDto);
    }

}