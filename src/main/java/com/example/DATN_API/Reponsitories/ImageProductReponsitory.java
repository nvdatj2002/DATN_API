package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.ImageProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductReponsitory extends JpaRepository<ImageProductEntity, Integer> {
}
