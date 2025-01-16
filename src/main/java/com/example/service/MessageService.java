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
    
    
}
