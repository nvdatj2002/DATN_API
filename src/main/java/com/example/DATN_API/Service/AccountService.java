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
        // Sử dụng accountRepository để lấy thông tin tài khoản từ cơ sở dữ liệu
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            // Xử lý trường hợp không tìm thấy tài khoản (nếu cần)
            // Ví dụ: nếu không tìm thấy, bạn có thể ném một ngoại lệ hoặc trả về null tùy theo yêu cầu của bạn.
            return null;
        }
    }
}
