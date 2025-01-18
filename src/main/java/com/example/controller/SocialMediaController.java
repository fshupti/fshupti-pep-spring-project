package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import java.util.List;



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

     @Autowired
     private AccountService accountService;


 
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

     @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable("messageId") Integer messageId) {
        // Check if the message exists
        Optional<Message> message = messageRepository.findById(messageId);

        // If the message doesn't exist, return 200 with an empty body
        if (message.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("");
        }

        // If the message exists, delete it and return 200 with '1' as the response body
        messageRepository.deleteById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // Retrieve all messages for a specific user
    @GetMapping("/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId) {
        // Fetch messages by user
        List<Message> messages = messageRepository.findByPostedBy(accountId);
        return ResponseEntity.ok(messages);
    }

 @PatchMapping("/{messageId}")
 public ResponseEntity<?> updateMessage(
        @PathVariable Integer messageId,
        @RequestBody Message updatedMessage) {
    // Check if the message exists in the database
    Optional<Message> existingMessageOpt = messageRepository.findById(messageId);
    if (existingMessageOpt.isEmpty()) {
        // Message not found
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message not found.");
    }

    // Validate the updated message text is not null or empty
    String newMessageText = updatedMessage.getMessageText();
    if (newMessageText == null || newMessageText.trim().isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot be empty.");
    }

    // Validate the updated message text length
    if (newMessageText.length() > 255) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message text cannot exceed 255 characters.");
    }

    // Perform the update
    Message existingMessage = existingMessageOpt.get();
    existingMessage.setMessageText(newMessageText);
    messageRepository.save(existingMessage);

    // Return success with number of rows updated (always 1 in this case)
    return ResponseEntity.status(HttpStatus.OK).body(1);
}

/** @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Account loginRequest) {
    Optional<Account> account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());

    if (account.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(account.get());
}
**/
/**
 * @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Account loginRequest) {
    // Validate input
    if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required.");
    }

    // Find the account by username
    Optional<Account> account = accountRepository.findByUsername(loginRequest.getUsername());
    if (account.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }

    // Check if the password matches
    if (!account.get().getPassword().equals(loginRequest.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }

    // Return success response
    return ResponseEntity.ok(account.get());
}
**/

@GetMapping("/messages")
public ResponseEntity<List<Message>> getAllMessages() {
    List<Message> messages = messageRepository.findAll();
    return ResponseEntity.ok(messages);
}


@GetMapping("/{messageId}")
public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
    Optional<Message> message = messageRepository.findById(messageId);
    if (message.isEmpty()) {
        return ResponseEntity.status(HttpStatus.OK).body(""); // Return empty body if message not found
    }
    return ResponseEntity.ok(message.get()); // Return the found message
}



}