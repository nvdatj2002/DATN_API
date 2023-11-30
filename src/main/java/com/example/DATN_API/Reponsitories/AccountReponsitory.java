
package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountReponsitory extends JpaRepository<Account, Integer> {
    @Query("select acc from Account acc")
    Page<Account> getAll(Pageable pageable);

//    @Query("select acc from Account acc where acc.username = ?1")
//    Account findByUserName(String username);
    @Query("select acc from Account acc where acc.us = ?1")
    Optional<Account> findByUsername(String username);
}

