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

    public MessageService(MessageRepository messagesRepository) {
        this.messageRepository = messagesRepository;
    }

    public MessageResponseDto postMessage(MessageRequestDto messageRequest) {

        Message newMessage = new Message();
        MessageResponseDto toReturnMessage = new MessageResponseDto();
        // en attente du service de login
        Long userId = (long) 1;

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
