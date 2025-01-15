package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Account;


import java.util.Optional;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 
 @RestController
 @RequestMapping("/messages")
 public class SocialMediaController {
 
     @Autowired
     private MessageRepository messageRepository;
 
     @Autowired
     private AccountRepository accountRepository; // for user existence validation.
 
     @PostMapping
     public ResponseEntity<?> createMessage(@RequestBody Message message) {
         // Validate message text is not blank
         if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot be blank.");
         }
 
         // Validate message text length
         if (message.getMessageText().length() > 255) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot exceed 255 characters.");
         }
 
         // Validate user exists
         Optional<Account> account = accountRepository.findById(message.getPostedBy());
         if (account.isEmpty()) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist.");
         }
 
         // Save the message
         Message savedMessage = messageRepository.save(message);
 
         // Return success response
         return ResponseEntity.ok(savedMessage);
     }
 }
 