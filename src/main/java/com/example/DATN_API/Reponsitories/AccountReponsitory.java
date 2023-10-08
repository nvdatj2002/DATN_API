package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReponsitory extends JpaRepository<Account, String> {
}
