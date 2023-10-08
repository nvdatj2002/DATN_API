package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Reponsitories.AccountReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountReponsitory accountReponsitory;

    public Page<Account> get(String keyword, String sort){

          };
}
