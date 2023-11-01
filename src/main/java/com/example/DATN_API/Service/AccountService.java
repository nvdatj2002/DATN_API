package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Reponsitories.AccountReponsitory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	AccountReponsitory accountReponsitory;

	public List<Account> findAll() {
		return accountReponsitory.findAll();
	}

	public Account findByUsername(String username) {
		return accountReponsitory.findByUsername(username);
	}

	public Account createAccount(Account account) {
		try {
			Account accountCreate = accountReponsitory.save(account);
			return accountCreate;
		} catch (Exception e) {
			e.printStackTrace();
			LogError.saveToLog(e);
		}
		return null;
	}

	public Account changePassword(String password, int id) {
		try {
			Account changePassword = accountReponsitory.changePassword(password, id);
			return changePassword;
		} catch (Exception e) {
			e.printStackTrace();
			LogError.saveToLog(e);
		}
		return null;
	}
}
