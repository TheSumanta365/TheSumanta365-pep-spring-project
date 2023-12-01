package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Component
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

   //for creating new user
    public Account addNewAccount(Account account){
        return accountRepository.save(account);
    }
    //fetching account by username
    public Account getAccountByUsername(String username){
        //return accountRepository.getAccountByUsername(username);
        return accountRepository.findByUsername(username);
    }
  
}




