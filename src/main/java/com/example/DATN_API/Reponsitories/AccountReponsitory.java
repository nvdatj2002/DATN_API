package com.example.DATN_API.Reponsitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.DATN_API.Entity.Account;

@Repository
public interface AccountReponsitory extends JpaRepository<Account, Integer> {
	@Query(value = "SELECT * FROM account where username=?1", nativeQuery = true)
	Account findByUsername(String username);
}

