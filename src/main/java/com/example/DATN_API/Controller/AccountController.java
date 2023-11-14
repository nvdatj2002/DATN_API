package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Entity.Role;
import com.example.DATN_API.Entity.RoleAccount;
import com.example.DATN_API.Service.AccountService;
import com.example.DATN_API.Service.JwtTokenProvider;
import com.example.DATN_API.Service.RoleAccountService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")

public class AccountController {
	@Autowired
	AccountService accountService;

	@Autowired
	RoleAccountService roleAccountService;

	@GetMapping()
	public ResponseEntity<ResponObject> getAllAccount() {
		return new ResponseEntity<>(new ResponObject("success", "OK VÀO", accountService.getListAccount()),
				HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponObject> login(@RequestBody Account a) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Account account = accountService.findByUsername(a.getUsername());
		if (account != null) {
			if (passwordEncoder.matches(a.getPassword(), account.getPassword())) {
				return new ResponseEntity<>(new ResponObject("success", "OK VÀO", account),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new ResponObject("error", "SAI PASS", null),
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new ResponObject("error", "TÀI KHOẢN KHÔNG TỒN TẠI!", null),
					HttpStatus.OK);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<ResponObject> register(@RequestBody Account a) {
		try {
			// CREATE ACCOUNT
			a.setCreatedate(new Date());
			a.setStatus(false);
			accountService.createAccount(a);
			// CREATE ACCOUNT ROLE
			Account account = accountService.findByUsername(a.getUsername());
			Role role = new Role();
			role.setId(1);
			RoleAccount roleAccount = new RoleAccount();
			roleAccount.setAccount(account);
			roleAccount.setRole(role);
			roleAccountService.createRoleAccount(roleAccount);
			return new ResponseEntity<>(new ResponObject("success", "OK CREATE", a), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ResponObject("error", "FAIL", null), HttpStatus.OK);
		}
	}
}