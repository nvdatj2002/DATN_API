package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.InfoAccount;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Service.AccountService;
import com.example.DATN_API.Service.InfoAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.nio.charset.Charset;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")

public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	InfoAccountService infoAccountService;

	@GetMapping()
	public ResponseEntity<ResponObject> getAll() {
		List<Account> accounts = accountService.findAll();
		if (accounts.size() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponObject("0", "Danh sách tài khoản trống!", ""));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponObject("1", "Danh sách tài khoản!", accounts));
		}
	}

	@GetMapping("/getuser")
	public ResponseEntity<Map<String, Object>> getByUsername(@RequestBody Account account) {
		Map<String, Object> response = new HashMap<>();
		Account accounts = accountService.findByUsername(account.getUsername());
		try {
			if (accounts != null)
				response.put("success", true);
			else
				response.put("success", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Account account) {
		Map<String, Object> response = new HashMap<>();
		try {
			Account accounts = accountService.findByUsername(account.getUsername());
			if (account.getUsername().equals(accounts.getUsername())
					&& account.getPassword().equals(accounts.getPassword()) && accounts.isStatus() == false) {
				response.put("success", true);
				response.put("message", "ĐĂNG NHẬP THÀNH CÔNG!");
				response.put("data", account);
			} else {
				if (account.getUsername().equals(accounts.getUsername())
						&& !account.getPassword().equals(accounts.getPassword())) {
					response.put("message", "MẬT KHẨU KHÔNG HỢP LỆ!");
				} else if (accounts.isStatus() == true) {
					response.put("message",
							"TÀI KHOẢN BẠN ĐĂNG NHẬP HIỆN TẠI ĐANG BỊ KHÓA, VUI LÒNG LIÊN HỆ CHO QUẢN TRỊ VIÊN NẾU GẶP VẪN ĐỀ!");
				} else {
					response.put("message", "ĐÚNG MỖI CÁI NỊT!");
				}
				response.put("success", false);
				response.put("data", account);
			}
		} catch (Exception e) {
			response.put("message", "TÊN ĐĂNG NHẬP KHÔNG HỢP LỆ!");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody Account account) {
		Map<String, Object> response = new HashMap<>();
		try {
			Account accounts = accountService.findByUsername(account.getUsername());
			Account createAcc = new Account();
			LocalDate localDate = LocalDate.now();
			Date date = java.sql.Date.valueOf(localDate);
			if (accounts == null) {
				// Account
				createAcc.setUsername(account.getUsername());
				createAcc.setPassword(account.getPassword());
				createAcc.setCreate_date(date);
				createAcc.setStatus(false);
				// Begin create new Account
				accountService.createAccount(createAcc);
				response.put("success", true);
				response.put("message", "ĐĂNG KÝ THÀNH CÔNG!");
				response.put("data", createAcc);
			} else {
				response.put("success", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/forgot")
	public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody InfoAccount inAccount) {
		Map<String, Object> response = new HashMap<>();
		try {
			byte[] array = new byte[7]; // length is bounded by 7
			new Random().nextBytes(array);
			String newPassword = new String(array, Charset.forName("UTF-8"));
			InfoAccount inAccounts = infoAccountService.findByEmail(inAccount.getEmail());
			accountService.changePassword(newPassword, inAccounts.getAccount().getId());
			response.put("success", true);
			response.put("message", "MẬT KHẨU CỦA BẠN ĐÃ ĐƯỢC GỬI QUA EMAIL!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}
}
