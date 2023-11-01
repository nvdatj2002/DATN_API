package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReponsitory extends JpaRepository<Account, String> {
	@Query(value = "SELECT * FROM account where username=?1", nativeQuery = true)
	Account findByUsername(String username);
	
	@Query(value = "update info_account set password=?1 where id=?2", nativeQuery = true)
	Account changePassword(String password, int id);
}

