package com.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfiguration {

    @org.springframework.beans.factory.annotation.Value("${api.cloudinary.apiKey}")
    private String apiKey;
    @org.springframework.beans.factory.annotation.Value("${api.cloudinary.secret}")
    private String secret;
    @org.springframework.beans.factory.annotation.Value("${api.cloudinary.cloudName}")
    private String cloudName;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", secret));
    }

}
