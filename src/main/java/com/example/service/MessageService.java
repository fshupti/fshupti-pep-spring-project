package com.example.service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessageById(Integer id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getMessagesByPostedBy(Integer postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public int updateMessage(int messageId, String messageText) {
        if (messageText == null || messageText.isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty.");
        }

        if (messageText.length() > 255) {
            throw new IllegalArgumentException("Message text exceeds the maximum length of 255 characters.");
        }

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found."));
        
        message.setMessageText(messageText);
        messageRepository.save(message);

        return 1; // One row updated
    }
    
    
}
