package com.example.service;

import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Transactional
    public Message createMessage(Message message) throws InvalidRequestException{
        //validate messageText before persisting to the db
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            throw new InvalidRequestException();
        }
        //embedds epoch time if property is null
        if(message.getTimePostedEpoch() == null){
            Long newEpochTimeSec = Instant.now().getEpochSecond();
            message.setTimePostedEpoch(newEpochTimeSec);
        }
        
        //persist the new message and return it
        Message createdMessage = this.messageRepository.save(message);
        return createdMessage;
    }

    public List<Message> getAllMessages(){
        //gets a list of ALL messages
        return this.messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        //gets message when retrieving by messageId
        return this.messageRepository.findById(messageId).orElse(null);
    }

    @Transactional
    public Integer deleteMessageById(int messageId){
        //checks if message existed to determine the return value
        boolean messageExists = this.messageRepository.existsById(messageId);

        if(!messageExists){
            return null;
        }

        int rowsAffected = this.messageRepository.deleteById(messageId);

        return rowsAffected;
        
    }

    @Transactional
    public Integer patchMessageText(Message patchMessage,int messageId) throws InvalidRequestException{
        String messageText = patchMessage.getMessageText();

        //validate the messageText property to be in a valid state before patching
        if(messageText.isBlank() || messageText.length() > 255){
            throw new InvalidRequestException();
        }

        //checks if messageId is valid otherwise throw exception
        this.messageRepository.findById(messageId).orElseThrow(()-> new InvalidRequestException());

        Integer rowsAffected = this.messageRepository.updateMessageTextByMessageId(messageText, messageId);
        
        return rowsAffected;
    }

    public List<Message> getAllMessagesByAccountId(int accountId){
        //returns the all messages from accountId
        return this.messageRepository.findAllByPostedBy(accountId);
    }
}
