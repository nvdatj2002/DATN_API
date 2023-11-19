package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.status=?1")
    List<Product> getProductbyStatus(int status);
    @Query("select p from Product p where p.status=?1")
    Page<Product> getPageProduct(int status, Pageable pageable);

 }
