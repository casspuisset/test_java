package com.api.controllers;

import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/rentals")
@Tag(name = "Rentals")
public class RentalController {

    private RentalService rentalsService;

    public RentalController(RentalService rentalService) {
        this.rentalsService = rentalService;
    }

    @Operation(description = "Get a rental by its ID", responses = {
            @ApiResponse(description = "Get the rental", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ApiResponse.class)), examples = @ExampleObject(value = "rentals:{\"id\":1,\"name\":\"test house 1\",\"surface\":432,\"price\":300,\"picture\":\"\"https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg\"\",\"description\":\"brief description\",\"owner_id\":1,\"created_at\":\"2012/12/02\",\"updated_at\":\"2012/12/02\"}"))
            }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
    }, security = { @SecurityRequirement(name = "bearerAuth") })
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

    @Operation(description = "Get all rentals", responses = {
            @ApiResponse(description = "Get all rentals", responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ApiResponse.class)), examples = @ExampleObject(value = "rentals:[{\"id\":1,\"name\":\"test house 1\",\"surface\":432,\"price\":300,\"picture\":\"\"https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg\"\",\"description\":\"brief description\",\"owner_id\":1,\"created_at\":\"2012/12/02\",\"updated_at\":\"2012/12/02\"}, {\"id\":1,\"name\":\"test house 2\",\"surface\":124,\"price\":200,\"picture\":\"\"https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg\"\",\"description\":\"anther brief description\",\"owner_id\":2,\"created_at\":\"2014/12/02\",\"updated_at\":\"2014/12/02\"}]"))
            }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
    }, security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("")
    public ResponseEntity<List<Rental>> getAllRentals() {
        var rentalsResponseDto = rentalsService.getAllRentals();
        return ResponseEntity.ok().body(rentalsResponseDto);
    }

    @Operation(description = "Create a new rental", responses = {
            @ApiResponse(description = "Rental created", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Rental created !\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
    }, security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "/**", consumes = "multipart/form-data")
    public ResponseEntity<RentalResponseDto> createRentals(@ModelAttribute RentalRequestDto rentalRequestDto) {
        // en attendant

        RentalResponseDto rentalResponseDto = rentalsService.createRentals(rentalRequestDto);
        return ResponseEntity.ok().body(rentalResponseDto);
    }

    @Operation(description = "Update an already existing rental", responses = {
            @ApiResponse(description = "Rental updated", responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class), examples = @ExampleObject(value = "{\"message\":\"Rental updated !\"}")) }),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
    }, security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping(path = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<RentalResponseDto> updateRentals(@PathVariable String id,
            @ModelAttribute RentalRequestDto rentalRequestDto) {

        RentalUpdateRequestDto newRequestDto = rentalsService.createUpdateDto(rentalRequestDto, id);
        RentalResponseDto rentalResponseDto = rentalsService.updateRentals(newRequestDto);
        return ResponseEntity.ok().body(rentalResponseDto);
    }

}