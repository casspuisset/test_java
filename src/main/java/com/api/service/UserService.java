package com.api.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.dto.LoginRequestDto;
import com.api.dto.RegisterRequestDto;
import com.api.model.DBUser;
import com.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> getUser(final LoginRequestDto loginRequestDto) {
        DBUser user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user != null) {
            log.warn("connexion réussie !");
            return ResponseEntity.ok("Login successful");
        } else {
            log.warn("Pas d'utilisateur reconnu");
            return ResponseEntity.badRequest().body("No User");
        }
    }

    public void register(RegisterRequestDto registerRequestDto) {
        DBUser user = userRepository.findByEmail(registerRequestDto.getEmail());
        if (user != null) {
            log.warn("L'utilisateur existe déjà avec ce mail");
        } else {
            String passwordEncoded = passwordEncoder.encode(registerRequestDto.getPassword());
            user = new DBUser();
            user.setEmail(registerRequestDto.getEmail());
            user.setPassword(passwordEncoded);
            user.setName(registerRequestDto.getName());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DBUser user = userRepository.findByEmail(email);

        if (user != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException("No user found with this email");
        }
    }
}