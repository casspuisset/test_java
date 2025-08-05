package com.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.api.dto.AuthResponseDto;
import com.api.dto.LoginRequestDto;
import com.api.dto.RegisterRequestDto;
import com.api.dto.UserDetailsDto;
import com.api.exceptions.UnauthorizedException;
import com.api.model.DBUser;
import com.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JWTService jwtService,
            AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    public AuthResponseDto login(final LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticateUser(loginRequestDto.getEmail(),
                    loginRequestDto.getPassword());
            log.info(authentication.getName());
            String token = jwtService.generateToken(authentication);
            return new AuthResponseDto(token);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new UnauthorizedException("echec de l'authentification de l'utilisateur");
        }

    }

    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        Optional<DBUser> optionnalUser = userRepository.findByEmail(registerRequestDto.getEmail());
        if (optionnalUser.isPresent()) {

            log.warn("User already exists");
            return null;

        } else {
            String passwordEncoded = passwordEncoder.encode(registerRequestDto.getPassword());
            DBUser user = new DBUser();
            user.setEmail(registerRequestDto.getEmail());
            user.setPassword(passwordEncoded);
            user.setName(registerRequestDto.getName());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            Authentication authentication = authenticateUser(registerRequestDto.getEmail(),
                    registerRequestDto.getPassword());
            String token = jwtService.generateToken(authentication);

            return new AuthResponseDto(token);
        }
    }

    private Authentication authenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return authentication;
        } catch (JwtException ex) {
            log.error("Unable to authenticate user");
            return null;
        }
    }

    public UserDetailsDto getUserById(Integer id) {
        Optional<DBUser> user = userRepository.findById(id);
        if (user != null) {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(id);
            userDetailsDto.setEmail(user.get().getEmail());
            userDetailsDto.setName(user.get().getName());
            userDetailsDto.setCreated_at(user.get().getCreatedAt());
            userDetailsDto.setUpdated_at(user.get().getUpdatedAt());
            return userDetailsDto;
        } else {
            return null;
        }
    }

    public UserDetailsDto getUserDetails() {

        Integer id = authenticationService.getAuthenticatedUser().getId();
        Optional<DBUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(id);
            userDetailsDto.setEmail(user.get().getEmail());
            userDetailsDto.setName(user.get().getName());
            userDetailsDto.setCreated_at(user.get().getCreatedAt());
            userDetailsDto.setUpdated_at(user.get().getUpdatedAt());
            return userDetailsDto;
        } else {
            return null;
        }
    }
}