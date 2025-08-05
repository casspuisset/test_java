package com.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageRequestDto {

    @NotBlank
    @Size(min = 3, max = 20, message = "l'envoi du message doit être compris entre 3 et 20 caractères")
    private String message;

    private Integer user_id;

    private Integer rental_id;
}
