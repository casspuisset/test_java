package com.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.dto.MessageRequestDto;
import com.api.model.Message;
import com.api.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messagesRepository) {
    }

    public Optional<Message> getMessage(final Long id) {
        return messageRepository.findById(id);
    }

    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message postMessage(MessageRequestDto message) {
        Message newMessage = new Message();

        newMessage.setUserId(message.getUser_id());
        newMessage.setRentalId(message.getRental_id());
        newMessage.setMessage(message.getMessage());
        newMessage.setCreatedAt(LocalDateTime.now());
        newMessage.setUpdatedAt(LocalDateTime.now());
        return messageRepository.save(newMessage);
    }
}
