package com.api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.api.dto.MessageRequestDto;
import com.api.dto.MessageResponseDto;
import com.api.model.Message;
import com.api.repository.MessageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

    private MessageRepository messageRepository;
    private final AuthenticationService authenticationService;

    public MessageService(MessageRepository messagesRepository, AuthenticationService authenticationService) {
        this.messageRepository = messagesRepository;
        this.authenticationService = authenticationService;
    }

    // create a new message
    public MessageResponseDto postMessage(MessageRequestDto messageRequest) {

        Message newMessage = new Message();
        MessageResponseDto toReturnMessage = new MessageResponseDto();
        Integer userId = authenticationService.getAuthenticatedUser().getId();

        var rentalId = messageRequest.getRental_id();
        var message = messageRequest.getMessage();

        newMessage.setUserId(userId);
        newMessage.setRentalId(rentalId);
        newMessage.setMessage(message);
        toReturnMessage.setMessage(message);
        newMessage.setCreatedAt(LocalDateTime.now());
        newMessage.setUpdatedAt(LocalDateTime.now());

        messageRepository.save(newMessage);

        return toReturnMessage;
    }
}
