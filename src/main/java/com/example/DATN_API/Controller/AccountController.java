package com.example.DATN_API.Controller;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.InfoAccount;
import com.example.DATN_API.Entity.ResponObject;
import com.example.DATN_API.Entity.Shop;
import com.example.DATN_API.Entity.AddressShop;
import com.example.DATN_API.Service.AccountService;
import com.example.DATN_API.Service.InfoAccountService;
import com.example.DATN_API.Service.MailServiceImplement;
import com.example.DATN_API.Service.ShopService;
import com.example.DATN_API.Service.AddressShopService;

import jakarta.validation.constraints.Email;

import com.example.DATN_API.Entity.MailInformation;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
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
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")

public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	InfoAccountService infoAccountService;

	@Autowired
	ShopService shopService;

	@Autowired
	AddressShopService addressService;

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
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/{email}")
	public ResponseEntity<Map<String, Object>> codeValidate(@PathVariable("email") String email) {
		Map<String, Object> response = new HashMap<>();
		try {
			String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
					+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			String charSet = "1234567890";
			// Begin validate Email
			if (Pattern.compile(regexPattern).matcher(email).matches() != true) {
				response.put("message", "EMAIL KHÔNG HỢP LỆ!");
			} else {
				String code = "";
				Random rand = new Random();
				int len = 8;
				for (int i = 0; i < len; i++) {
					code += charSet.charAt(rand.nextInt(charSet.length()));
				}
				MailInformation mail = new MailInformation();
				mail.setTo(email);
				mail.setSubject("Quên mật khẩu");
				mail.setBody("<html><body>" + "<p>Xin chào " + email + ",</p>"
						+ "<p>Chúng tôi nhận được yêu cầu đăng ký tài khoản Diamond Fashion của bạn.</p>"
						+ "<p>Vui lòng không chia sẽ mã này cho bất cứ ai:" + "<h3>" + code + "</h3>" + "</p>"
						+ "<p>Trân trọng,</p>"
						+ "<p>Bạn có thắc mắc? Liên hệ chúng tôi tại đây khuong8177@gmail.com.</p>" + "</body></html>");
				mailServiceImplement.send(mail);
				response.put("code", code);
				response.put("message", "MÃ XÁC NHẬN ĐÃ ĐƯỢC GỬI QUA MAIL CỦA BẠN");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register/{email}")
	public ResponseEntity<Map<String, Object>> register(@PathVariable("email") String email,
			@RequestBody Account account) {
		Map<String, Object> response = new HashMap<>();
		try {
			Account accounts = accountService.findByUsername(account.getUsername());
			Account createAcc = new Account();
			InfoAccount inAcc = new InfoAccount();
			LocalDate localDate = LocalDate.now();
			Date date = java.sql.Date.valueOf(localDate);
			// Begin validate
			if (accounts != null) {
				response.put("message", " TÊN TÀI KHOẢN ĐÃ TỒN TẠI!");
			} else if (account.getUsername().length() < 6) {
				response.put("message", "TÊN TÀI KHOẢN QUÁ NGẮN!");
			} else if (account.getPassword().length() < 6) {
				response.put("message", "MẬT KHẨU QUÁ NGẮN!");
			} else if (infoAccountService.findByEmail(email) != null) {
				response.put("message", "EMAIL NÀY ĐÃ ĐƯỢC SỬ DỤNG CHO MỘT TÀI KHOẢN KHÁC!");
			} else {
				// Account
				createAcc.setUsername(account.getUsername());
				createAcc.setPassword(account.getPassword());
				createAcc.setCreatedate(date);
				createAcc.setStatus(false);
				// Begin create new Account
				accountService.createAccount(createAcc);
				// Default info
				Account findAcc = accountService.findByUsername(account.getUsername());
				inAcc.setFullname(findAcc.getUsername());
				inAcc.setEmail(email);
				inAcc.setInfaccount(findAcc);
				infoAccountService.createProfile(inAcc);
				response.put("success", true);
				response.put("message", "ĐĂNG KÝ THÀNH CÔNG!");
				response.put("data", createAcc);
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
			String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
					+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			String charSet = "abcdefghiklmnopqrstuwvxyz" + "1234567890" + "!@#%&*";
			// Begin validate Email
			if (Pattern.compile(regexPattern).matcher(inAccount.getEmail()).matches() != true) {
				response.put("message", "EMAIL KHÔNG HỢP LỆ!");
			} else {
				InfoAccount inAccounts = infoAccountService.findByEmail(inAccount.getEmail());
				Account account = accountService.findByUsername(inAccounts.getInfaccount().getUsername());
				if (inAccounts.getInfaccount().isStatus() == true) {
					response.put("message",
							"HIỆN TẠI, TÀI KHOẢN CỦA BẠN ĐANG BỊ KHÓA, VUI LÒNG LIÊN HỆ CSKH ĐỂ ĐƯỢC HỔ TRỢ SỚM NHẤT!");
				} else {
					// Email = true, then begin random new password and update
					String newPassword = "";
					Random rand = new Random();
					int len = 12;
					for (int i = 0; i < len; i++) {
						newPassword += charSet.charAt(rand.nextInt(charSet.length()));
					}
					account.setPassword(newPassword);
					accountService.changePass(account);
					MailInformation mail = new MailInformation();
					mail.setTo(inAccount.getEmail());
					mail.setSubject("Quên mật khẩu");
					mail.setBody("<html><body>" + "<p>Xin chào " + account.getUsername() + ",</p>"
							+ "<p>Chúng tôi nhận được yêu cầu thiết lập lại mật khẩu cho tài khoản Diamond Fashion của bạn.</p>"
							+ "<p>Vui lòng không chia sẽ mật khẩu này cho bất cứ ai:" + "<h3>" + newPassword + "</h3>"
							+ "</p>"
							+ "<p>Nếu bạn không yêu cầu thiết lập lại mật khẩu, vui lòng liên hệ Bộ phận Chăm sóc Khách hàng tại đây</p>"
							+ "<p>Trân trọng,</p>"
							+ "<p>Bạn có thắc mắc? Liên hệ chúng tôi tại đây khuong8177@gmail.com.</p>"
							+ "</body></html>");
					mailServiceImplement.send(mail);
					response.put("success", true);
					response.put("message", "MẬT KHẨU CỦA BẠN ĐÃ ĐƯỢC GỬI QUA EMAIL!");
				}
			}
		} catch (Exception e) {
			response.put("message", "KHÔNG TÌM THẤY TÀI KHOẢN CÓ EMAIL " + inAccount.getEmail());
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/profile")
	public ResponseEntity<InfoAccount> profileAccount() {
		if (infoAccountService.findById_account(5) != null) {
			return new ResponseEntity<>(infoAccountService.findById_account(5), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/updateprofile/{username}")
	public ResponseEntity<Map<String, Object>> updateProfile(@PathVariable("username") String username,
			@RequestBody InfoAccount inAccount) {
		Map<String, Object> response = new HashMap<>();
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		try {
			if (inAccount.getFullname() == null || inAccount.getPhone() == null || inAccount.getId_card() == null
					|| inAccount.getEmail() == null) {
				response.put("message", "VUI LÒNG NHẬP ĐẦY ĐỦ NHỮNG THÔNG TIN QUAN TRỌNG!");
			} else {
				Account account = accountService.findByUsername(username);
				InfoAccount inAccounts = infoAccountService.findById_account(account.getId());
				InfoAccount inCheck = infoAccountService.findByEmail(inAccount.getEmail());
				if (inAccount.getPhone().length() != 10) {
					response.put("message", "SỐ ĐIỆN THOẠI KHÔNG HỢP LỆ!");
				} else if (inAccount.getId_card().length() != 12) {
					response.put("message", "SỐ CĂN CƯỚC CÔNG DÂN KHÔNG HỢP LỆ!");
				} else if (Pattern.compile(regexPattern).matcher(inAccount.getEmail()).matches() != true) {
					response.put("message", "EMAIL KHÔNG HỢP LỆ!");
				} else if (inCheck != null && inCheck.getInfaccount().getId() != account.getId()) {
					response.put("message", "EMAIL NÀY ĐÃ ĐƯỢC SỬ DỤNG CHO MỘT TÀI KHOẢN KHÁC!");
				} else {
					int phone = Integer.parseInt(inAccount.getPhone());
					inAccount.setInfaccount(account);
					inAccount.setId(inAccounts.getId());
					infoAccountService.createProfile(inAccount);
					response.put("message", "CẬP NHẬT THÔNG TIN THÀNH CÔNG!");
				}
			}
		} catch (NumberFormatException e) {
			response.put("message", "SAI ĐỊNH DẠNG SỐ ĐIỆN THOẠI, VUI LÒNG CHỈ NHẬP CÁC SỐ 0 - 9!");
			e.printStackTrace();
		} catch (Exception e) {
			response.put("message", "Lỗi CẬP NHẬT PROFILE!");
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/changepass")
	public ResponseEntity<Map<String, Object>> changePass(@RequestBody Account account) {
		Map<String, Object> response = new HashMap<>();
		try {
			Account accounts = accountService.findByUsername(account.getUsername());
			if (account.getPassword().length() < 6) {
				response.put("message", "MẬT KHẨU QUÁ NGẮN!");
			} else {
				accounts.setPassword(account.getPassword());
				accountService.changePass(accounts);
				response.put("success", true);
				response.put("message", "ĐỔI MẬT KHẨU THÀNH CÔNG!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "LỖI THAY ĐỔI MẬT KHẨU");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/saleregis/{username}/{shop}")
	public ResponseEntity<Map<String, Object>> saleRegis(@PathVariable("username") String username,
			@PathVariable("shop") String shop_name, @RequestBody AddressShop address) {
		Map<String, Object> response = new HashMap<>();
		LocalDate localDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(localDate);
		try {
			Account accounts = accountService.findByUsername(username);
			if (shopService.existByAccount(accounts.getId()) == true) {
				response.put("message",
						"BẠN ĐÃ GỬI 1 YÊU CẦU ĐĂNG KÝ LÊN HỆ THỐNG, VUI LÒNG CHỜ PHẢN HỒI TỪ CHÚNG TÔI ĐỂ TIẾP TỤC!");
			} else {
				Shop shop = new Shop();
				// Create shop
				Account account = accountService.findByUsername(username);
				shop.setAccountShop(account);
				shop.setShop_name(shop_name);
				shop.setCreate_date(date);
				shop.setStatus(0);
				shopService.createShop(shop);
				// Create shop address
				address.setShopAddress(shop);
				addressService.createAddressShop(address);
				response.put("message", "GỬI YÊU CẦU THÀNH CÔNG, VUI LÒNG CHỜ XÉT DUYỆT!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "LỖI ĐĂNG KÝ BÁN HÀNG");
		}
		return ResponseEntity.ok(response);
	}
}
