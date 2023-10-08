package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.ImageProduct;
import com.example.DATN_API.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductReponsitory extends JpaRepository<ImageProduct, Integer> {
}
