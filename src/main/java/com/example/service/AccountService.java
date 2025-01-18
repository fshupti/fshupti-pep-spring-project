package com.example.service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> findAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> login(String username, String password) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account;
        } else {
            return Optional.empty();
        }
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (account.getPassword() == null || account.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        return accountRepository.save(account); // Return the saved account object
    }
}
