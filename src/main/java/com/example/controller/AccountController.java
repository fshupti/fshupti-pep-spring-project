package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
//import com.example.service.AccountService;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling account-related operations such as retrieving user messages and login.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private MessageRepository messageRepository; // To fetch messages for a user

    @Autowired
    private AccountRepository accountRepository; // To check if an account exists

   /** @Autowired
    private AccountService accountService; // Service for additional business logic (if needed) **/


    // Retrieve all messages for a specific user
    @GetMapping("/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId) {
        // Fetch messages posted by the user
        List<Message> messages = messageRepository.findByPostedBy(accountId);
        
        if (messages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messages);
        }

        return ResponseEntity.ok(messages);
    }

    // Login endpoint
    @PostMapping("/login")
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

        // Return success response with account details
        return ResponseEntity.ok(account.get());
    }

}

    

