package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.OrderCustomer;
import com.example.DATN_API.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderReponsitory extends JpaRepository<OrderCustomer, Integer> {

}