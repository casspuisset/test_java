package com.api.dto;

import lombok.Data;

@Data
public class MessageRequestDto {

    private String message;

    private Long user_id;

    private Long rental_id;
}
