package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.OrderDetail;
import com.example.DATN_API.Reponsitories.OrderDetailReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailReponsitory orderDetailReponsitory;
    public OrderDetail save(OrderDetail orderDetail){
        return  orderDetailReponsitory.save(orderDetail);
    }
}
