package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.CategoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryItemReponsitory extends JpaRepository<CategoryItemEntity, Integer> {
}
