package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.AddressShop;
import com.example.DATN_API.Entity.RoleAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAccountReponsitory extends JpaRepository<RoleAccount, Integer> {
}
