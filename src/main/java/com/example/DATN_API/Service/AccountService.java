package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Reponsitories.AccountReponsitory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountReponsitory accountRepository;

    public Account getAccountById(int accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else { return null;
        }
    }
}
