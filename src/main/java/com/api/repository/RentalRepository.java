package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    public Rental findById(Integer id);
}
