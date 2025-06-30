package com.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class RessourceController {

    @GetMapping("/")
    public String getRessource() {
        return "une ressource";
    }

}
