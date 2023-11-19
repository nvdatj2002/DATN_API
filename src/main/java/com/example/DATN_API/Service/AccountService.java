package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.Order;
import com.example.DATN_API.Reponsitories.AccountReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountReponsitory accountReponsitory;
    public Page<Account> findAll(Optional<Integer> offset, Optional<Integer> sp, Optional<String> field){
        String sort = field.orElse("create_date");
        int itemStart = offset.orElse(0);;
        int sizePage = sp.orElse(10);
        return accountReponsitory.getAll(PageRequest.of(itemStart,sizePage, Sort.Direction.DESC,sort));
    }
    public Account findAccountById(Integer id) {
        Optional<Account> acc = accountReponsitory.findById(id);
        return acc.get();
    }
    public Account banAccount(Integer id) {
        Optional<Account> acc = accountReponsitory.findById(id);
        Account account = acc.get();
        account.setStatus(false);
        accountReponsitory.save(account);
        return account;
    }
}
