package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.InfoAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoAccountReponsitory extends JpaRepository<InfoAccount, String> {
	@Query(value = "SELECT * FROM info_account where email=?1", nativeQuery = true)
	InfoAccount findByEmail(String email);
}
