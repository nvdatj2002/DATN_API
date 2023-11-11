package com.example.DATN_API.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Entity.OrderCustomer;
import com.example.DATN_API.Reponsitories.OrderReponsitory;

@Service
public class OrderService {
    @Autowired
    OrderReponsitory orderReponsitory;

    public List<OrderCustomer> findAll() {
        return orderReponsitory.findAll();
    }

}
