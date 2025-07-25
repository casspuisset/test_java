package com.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.dto.RentalRequestDto;
import com.api.dto.RentalResponseDto;
import com.api.dto.RentalUpdateRequestDto;
import com.api.model.Rental;
import com.api.repository.RentalRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RentalService {

    private final RentalRepository rentalsRepository;
    private final ImageService imageService;

    public RentalService(RentalRepository rentalRepository, ImageService imageService) {
        this.rentalsRepository = rentalRepository;
        this.imageService = imageService;
    }

    public Rental getRental(final Integer id) {
        var rental = rentalsRepository.findById(id);
        return rental.get();
    }

    public List<Rental> getAllRentals() {
        return rentalsRepository.findAll();
    }

    public RentalResponseDto createRentals(RentalRequestDto rental) {
        Rental newRental = new Rental();
        // en attente du service de login
        Long userId = (long) 1;
        String picture = imageService.uploadImage(rental.getPicture());

        newRental.setName(rental.getName());
        newRental.setSurface(rental.getSurface());
        newRental.setPrice(rental.getPrice());
        newRental.setPicture(picture);
        newRental.setDescription(rental.getDescription());
        newRental.setOwnerId(userId);
        newRental.setCreatedAt(LocalDateTime.now());
        newRental.setUpdatedAt(LocalDateTime.now());

        RentalResponseDto rentalResponseDto = new RentalResponseDto();

        rentalsRepository.save(newRental);
        rentalResponseDto.setMessage("Rental created");
        return rentalResponseDto;
    }

    public RentalResponseDto updateRentals(RentalUpdateRequestDto rental) {
        var searchedRental = rentalsRepository.findById(rental.getId());
        if (searchedRental != null) {
            var newRental = searchedRental.get();
            // en attente du service de login
            Long userId = (long) 1;

            newRental.setName(rental.getName());
            newRental.setSurface(rental.getSurface());
            newRental.setPrice(rental.getPrice());
            if (rental.getPicture() != null) {
                newRental.setPicture(rental.getPicture().getName());
            } else {
                newRental.setPicture(newRental.getPicture());
            }
            newRental.setDescription(rental.getDescription());
            newRental.setOwnerId(userId);
            newRental.setCreatedAt(newRental.getCreatedAt());
            newRental.setUpdatedAt(LocalDateTime.now());

            RentalResponseDto rentalResponseDto = new RentalResponseDto();

            rentalsRepository.save(newRental);
            rentalResponseDto.setMessage("Rental updated");
            return rentalResponseDto;
        } else {
            return null;
        }
    }

    public RentalUpdateRequestDto createUpdateDto(RentalRequestDto rentalRequestDto, String id) {
        var newRequest = new RentalUpdateRequestDto();
        newRequest.setDescription(rentalRequestDto.getDescription());
        newRequest.setName(rentalRequestDto.getName());
        newRequest.setPicture(rentalRequestDto.getPicture());
        newRequest.setPrice(rentalRequestDto.getPrice());
        newRequest.setSurface(rentalRequestDto.getSurface());
        newRequest.setId(Integer.parseInt(id));
        return newRequest;
    }
}
