package com.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.api.dto.AuthResponseDto;
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
    private JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JWTService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto login(final LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()));
        String token = jwtService.generateToken(authentication);

        return new AuthResponseDto(token);

    }

    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        DBUser user = userRepository.findByEmail(registerRequestDto.getEmail());
        if (user != null) {

            log.warn("User already exists");
            return null;

        } else {
            log.info("user not exist");
            String passwordEncoded = passwordEncoder.encode(registerRequestDto.getPassword());
            user = new DBUser();
            user.setEmail(registerRequestDto.getEmail());
            user.setPassword(passwordEncoded);
            user.setName(registerRequestDto.getName());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            Authentication authentication = authenticateUser(registerRequestDto.getEmail(),
                    registerRequestDto.getPassword());
            String token = jwtService.generateToken(authentication);

            return new AuthResponseDto("token");
        }
    }

    private Authentication authenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return authentication;
        } catch (JwtException ex) {
            log.error("Unable to authenticate user. {}", ex.getMessage());
            return null;
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

    public Optional<DBUser> getUserById(Long id) {
        Optional<DBUser> user = userRepository.findById(id);
        return user;
    }

    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        try {

            DBUser user = userRepository.findByEmail(authentication.getName());

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(null);
            }

            return ResponseEntity.ok(user);

        } catch (Exception error) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }
}