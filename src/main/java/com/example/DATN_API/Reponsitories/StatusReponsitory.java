package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.AddressShop;
import com.example.DATN_API.Entity.Product;
import com.example.DATN_API.Entity.Shop;
import com.example.DATN_API.Entity.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusReponsitory extends JpaRepository<Status, Integer> {
}
