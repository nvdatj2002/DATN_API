package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopReponsitory extends JpaRepository<ShopEntity, Integer> {
}
