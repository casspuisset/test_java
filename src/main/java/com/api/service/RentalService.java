package com.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.dto.AllRentalsDto;
import com.api.dto.RentalDto;
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
    private final AuthenticationService authenticationService;

    public RentalService(RentalRepository rentalRepository, ImageService imageService,
            AuthenticationService authenticationService) {
        this.rentalsRepository = rentalRepository;
        this.imageService = imageService;
        this.authenticationService = authenticationService;
    }

    // get a rental by their id
    public RentalDto getRental(final Integer id) {
        var rental = rentalsRepository.findById(id);
        var response = rentalToDto(rental.get());
        return response;
    }

    // get all rentals
    public AllRentalsDto getAllRentals() {
        var rentals = new AllRentalsDto();
        var listRentals = rentalsRepository.findAll();
        var listRentalDto = listRentals.stream().map(this::rentalToDto).toList();
        rentals.setRentals(listRentalDto);
        return rentals;
    }

    // map a rental in a Dto
    private RentalDto rentalToDto(Rental rental) {
        RentalDto rentalDto = new RentalDto();
        rentalDto.setId(rental.getId());
        rentalDto.setName(rental.getName());
        rentalDto.setDescription(rental.getDescription());
        rentalDto.setOwner_id(rental.getOwnerId());
        rentalDto.setCreated_at(rental.getCreatedAt());
        rentalDto.setUpdated_at(rental.getUpdatedAt());
        rentalDto.setPicture(rental.getPicture());
        rentalDto.setPrice(rental.getPrice());
        rentalDto.setSurface(rental.getSurface());
        return rentalDto;
    }

    // create a new rental
    public RentalResponseDto createRentals(RentalRequestDto rental) {
        Rental newRental = new Rental();
        Integer id = authenticationService.getAuthenticatedUser().getId();
        String picture = imageService.uploadImage(rental.getPicture());

        newRental.setName(rental.getName());
        newRental.setSurface(rental.getSurface());
        newRental.setPrice(rental.getPrice());
        newRental.setPicture(picture);
        newRental.setDescription(rental.getDescription());
        newRental.setOwnerId(id);
        newRental.setCreatedAt(LocalDateTime.now());
        newRental.setUpdatedAt(LocalDateTime.now());

        RentalResponseDto rentalResponseDto = new RentalResponseDto();

        rentalsRepository.save(newRental);
        rentalResponseDto.setMessage("Rental created");
        return rentalResponseDto;
    }

    // edit an existing rental
    public RentalResponseDto updateRentals(RentalUpdateRequestDto rental) {
        Optional<Rental> searchedRental = rentalsRepository.findById(rental.getId());
        if (searchedRental != null) {
            Rental newRental = searchedRental.get();
            Integer id = authenticationService.getAuthenticatedUser().getId();

            newRental.setName(rental.getName());
            newRental.setSurface(rental.getSurface());
            newRental.setPrice(rental.getPrice());
            if (rental.getPicture() != null) {
                String picture = imageService.uploadImage(rental.getPicture());

                newRental.setPicture(picture);
            } else {
                newRental.setPicture(newRental.getPicture());
            }
            newRental.setDescription(rental.getDescription());
            newRental.setOwnerId(id);
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

    // map a Rental in a Dto for updating
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
