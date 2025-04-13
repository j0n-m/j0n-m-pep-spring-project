package com.example.service;

import java.security.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidRequestException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) throws InvalidRequestException{
        //successful if and only if the messageText is not blank, is not over 255 characters, 
        //and postedBy refers to a real, existing user
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            throw new InvalidRequestException();
        }
        if(message.getTimePostedEpoch() == null){
            Long newEpochTime = System.currentTimeMillis();
            message.setTimePostedEpoch(newEpochTime);
        }
        
        Message createdMessage = this.messageRepository.save(message);
        return createdMessage;
    }
}
