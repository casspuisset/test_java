package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.model.DBUser;

@Repository
public interface UserRepository extends JpaRepository<DBUser, Long> {
    public DBUser findByEmail(String email);

    public DBUser findByIntId(Integer id);
}
