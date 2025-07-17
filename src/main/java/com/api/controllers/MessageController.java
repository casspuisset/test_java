package com.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.MessageRequestDto;
import com.api.dto.MessageResponseDto;
import com.api.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages/")
    public ResponseEntity<MessageResponseDto> postMessage(@RequestBody MessageRequestDto message) {
        var messageResponseDto = messageService.postMessage(message);
        return ResponseEntity.ok().body(messageResponseDto);
    }
}
