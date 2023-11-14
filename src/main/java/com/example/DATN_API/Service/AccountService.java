package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Reponsitories.AccountReponsitory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	AccountReponsitory accountReponsitory;

	public List<Account> findAll() {
		return accountReponsitory.findAll();
	}

	public Account findByUsername(String username) {
		return accountReponsitory.findByUsername(username).get();
	}

	public Account createAccount(Account account) {
		try {
			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			Account accountCreate = accountReponsitory.save(account);
			accountCreate.setPassword(passwordEncoder.encode(accountCreate.getPassword()));
			return accountCreate;
		} catch (Exception e) {
			e.printStackTrace();
			LogError.saveToLog(e);
		}
		return null;
	}

	public Account findById(int id) {
		return accountReponsitory.findById(id).get();
	}

	public Account changePass(Account account) {
		try {
			Account accountCreate = accountReponsitory.save(account);
			return accountCreate;
		} catch (Exception e) {
			e.printStackTrace();
			LogError.saveToLog(e);
		}
		return null;
	}

	public List<Account> getListAccount() {
		return accountReponsitory.getListAccount();
	}
}