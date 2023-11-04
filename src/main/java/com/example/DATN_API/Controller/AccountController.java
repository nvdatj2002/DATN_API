package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.InfoAccount;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Service.AccountService;
import com.example.DATN_API.Service.InfoAccountService;
import com.example.DATN_API.Service.MailServiceImplement;
import com.example.DATN_API.Entity.MailInformation;

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
	
	@Autowired
	MailServiceImplement mailServiceImplement;

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
			String charSet = "abcdefghiklmnopqrstuwvxyz" + "1234567890" + "!@#%&*";
			String newPassword = "";
			Random rand = new Random();
			int len = 12;
			for (int i = 0; i < len; i++) {
				newPassword += charSet.charAt(rand.nextInt(charSet.length()));
			}
			InfoAccount inAccounts = infoAccountService.findByEmail(inAccount.getEmail());
			Account account = new Account();
			account.setId(inAccounts.getAccount().getId());
			account.setUsername(inAccounts.getAccount().getUsername());
			account.setCreate_date(inAccounts.getAccount().getCreate_date());
			account.setPassword(newPassword);
			account.setStatus(inAccounts.getAccount().isStatus());
			account.setId(inAccounts.getAccount().getId());
			accountService.createAccount(account);
			MailInformation mail = new MailInformation();
			mail.setTo(inAccount.getEmail());
			mail.setSubject("Quên mật khẩu");
			mail.setBody("<html><body>" + "<p>Xin chào " + account.getUsername() + ",</p>"
					+ "<p>Chúng tôi nhận được yêu cầu thiết lập lại mật khẩu cho tài khoản Diamond Fashion của bạn.</p>"
					+ "<p>Vui lòng không chia sẽ mật khẩu này cho bất cứ ai:" + "<h3>" + newPassword + "</h3>" + "</p>"
					+ "<p>Nếu bạn không yêu cầu thiết lập lại mật khẩu, vui lòng liên hệ Bộ phận Chăm sóc Khách hàng tại đây</p>"
					+ "<p>Trân trọng,</p>"
					+ "<p>Bạn có thắc mắc? Liên hệ chúng tôi tại đây khuong8177@gmail.com.</p>"
					+ "</body></html>");
			mailServiceImplement.send(mail);
			response.put("success", true);
			response.put("message", "MẬT KHẨU CỦA BẠN ĐÃ ĐƯỢC GỬI QUA EMAIL!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/profile")
	public ResponseEntity<Map<String, Object>> profile(@RequestBody InfoAccount inAccount) {
		Map<String, Object> response = new HashMap<>();
		try {
			InfoAccount inAccounts = infoAccountService.findById_account(2);
			response.put("success", true);
			response.put("fullname", inAccounts.getFullname());
			response.put("phone", inAccounts.getPhone());
			response.put("email", inAccounts.getEmail());
			response.put("city", "Ho Chi Minh");
			response.put("district", "Quan 12");
			response.put("ward", "Dong Hung Thuan");
			response.put("address", "195 Nguyen Van Qua");
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "TÀI KHOẢN CỦA BẠN CHƯA CÓ THÔNG TIN, VUI LÒNG CẬP NHẬT ĐẦY ĐỦ THÔNG TIN!");
		}
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/updateprofile")
	public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody InfoAccount inAccount) {
		Map<String, Object> response = new HashMap<>();
		try {
			Account account = accountService.findByUsername("account1");
			InfoAccount inAccounts = infoAccountService.findById_account(account.getId());
			inAccount.setAccount(account);
			inAccount.setId(inAccounts.getId());
			infoAccountService.createProfile(inAccount);
			response.put("message", "CẬP NHẬT THÔNG TIN THÀNH CÔNG!");
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Lỗi!");
		}
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/changepass")
	public ResponseEntity<Map<String, Object>> changePass(@RequestBody Account account) {
		Map<String, Object> response = new HashMap<>();
		try {
			response.put("message", "Mật khẩu của bạn đã được thay đổi");
			response.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}
}
