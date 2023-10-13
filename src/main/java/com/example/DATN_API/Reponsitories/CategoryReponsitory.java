package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.CategoryEntity;
import com.example.DATN_API.Entity.CategoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReponsitory extends JpaRepository<CategoryEntity, Integer> {

}
