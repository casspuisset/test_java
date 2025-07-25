package com.api.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    @org.springframework.beans.factory.annotation.Value("${security.jwt.key}")
    private String jwtKey;

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http)
    // throws Exception {
    // return http
    // .csrf(csrf -> csrf.disable())
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .authorizeHttpRequests(auth -> auth.requestMatchers("api/auth/register",
    // "api/auth/login").permitAll()
    // .anyRequest().authenticated())
    // .httpBasic(Customizer.withDefaults())
    // .build();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService users() {

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder()
                        .encode("password"))
                .build();
        return new InMemoryUserDetailsManager(user);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public JwtEncoder jwtEncoder() {

        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));

    }

    @Bean
    public JwtDecoder jwtDecoder() {

        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();

    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
