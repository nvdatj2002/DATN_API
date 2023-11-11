package com.example.DATN_API.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Entity.OrderCustomer;
import com.example.DATN_API.Entity.OrderDetail;
import com.example.DATN_API.Reponsitories.OrderDetailReponsitory;
import com.example.DATN_API.Reponsitories.OrderReponsitory;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailReponsitory orderReponsitory;

    public List<OrderDetail> findAll() {
        return orderReponsitory.findAll();
    }

    public List<OrderDetail> findByIdOrder(int idOrder){
        return orderReponsitory.findByIdOrder(idOrder);
    }

    public List<Object[]> getTotalByMonth(int idShop){
        return orderReponsitory.getTotalByMonth(idShop);
    }
}
