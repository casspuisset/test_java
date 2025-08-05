package com.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.DBUser;

@Repository
public interface UserRepository extends JpaRepository<DBUser, Integer> {
    public Optional<DBUser> findByEmail(String email);

    public Optional<DBUser> findById(Integer id);
}
