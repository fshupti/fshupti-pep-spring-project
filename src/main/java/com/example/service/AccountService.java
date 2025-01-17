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
    
    
}
