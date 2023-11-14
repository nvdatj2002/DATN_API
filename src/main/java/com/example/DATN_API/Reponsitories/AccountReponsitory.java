package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Account;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountReponsitory extends JpaRepository<Account, Integer> {
	@Query("Select a FROM Account a WHERE a.username = ?1")
	Optional<Account> findByUsername(String username);

	@Query("Select a FROM Account a")
	List<Account> getListAccount();
}