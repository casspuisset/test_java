package com.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    public Optional<Rental> findById(Integer id);
}
