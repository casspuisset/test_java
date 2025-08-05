package com.api.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.dto.UserDetailsDto;
import com.api.model.DBUser;
import com.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {

    private UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Optional<DBUser> getAuthenticatedUserFromContext() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return Optional.of(authentication)
                    .map(Authentication::getName)
                    .flatMap(userRepository::findByEmail);
        } catch (Exception ex) {
            log.error("### GET AUTHENTICATED USER ### No authenticated user found in context");
            return Optional.empty();
        }

    }

    public UserDetailsDto getAuthenticatedUser() {
        return getAuthenticatedUserFromContext()
                .map(this::mapTo)
                .orElseThrow(() -> {
                    throw null;
                });

    }

    private UserDetailsDto mapTo(DBUser dbUser) {
        var userDetailsDto = new UserDetailsDto();
        userDetailsDto.setName(dbUser.getName());
        userDetailsDto.setEmail(dbUser.getEmail());
        userDetailsDto.setId(dbUser.getId());
        userDetailsDto.setCreated_at(dbUser.getCreatedAt());
        userDetailsDto.setUpdated_at(dbUser.getUpdatedAt());
        return userDetailsDto;
    }
}
