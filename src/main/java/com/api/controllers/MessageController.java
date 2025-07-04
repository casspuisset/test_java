package com.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.MessageRequestDto;
import com.api.model.Message;
import com.api.service.MessageService;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public Message postMessage(MessageRequestDto message) {
        return messageService.postMessage(message);
    }
}
