package com.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.dto.RentalsRequestDto;
import com.api.model.Rental;
import com.api.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {

    private RentalRepository rentalsRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalsRepository = rentalRepository;
    }

    public Optional<Rental> getRental(final Long id) {
        return rentalsRepository.findById(id);
    }

    public Iterable<Rental> getAllRentals() {
        return rentalsRepository.findAll();
    }

    public Rental postRentals(RentalsRequestDto rental) {
        Rental newRental = new Rental();

        newRental.setName(rental.getName());
        newRental.setSurface(rental.getSurface());
        newRental.setPrice(rental.getPrice());
        newRental.setPicture(rental.getPicture());
        newRental.setDescription(rental.getDescription());
        newRental.setOwnerId(rental.getOwnerId());
        newRental.setCreatedAt(LocalDateTime.now());
        newRental.setUpdatedAt(LocalDateTime.now());

        return rentalsRepository.save(newRental);
    }
}
