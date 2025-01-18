package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
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


    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

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

    // Return a 200 OK with an empty list if no messages are found
    return ResponseEntity.ok(messages);
}


    // Login endpoint
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Account loginRequest) {
    Optional<Account> account = accountRepository.findByUsername(loginRequest.getUsername());
    if (account.isEmpty() || !account.get().getPassword().equals(loginRequest.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(account.get());
}

@PostMapping
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        try {
            // Call the register method from AccountService
            Account newAccount = accountService.register(account);
            return ResponseEntity.ok(newAccount);
        } catch (IllegalArgumentException e) {
            // Return a 409 if the username already exists
            return ResponseEntity.status(409).body(null);
        }
    }

}

    

