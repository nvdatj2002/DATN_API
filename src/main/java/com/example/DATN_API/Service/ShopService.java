package com.example.DATN_API.Service;


import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.ShopReponsitory;

@Service
public class ShopService {
    @Autowired
    ShopReponsitory ShopReponsitory;

    public List<Shop> findAll() {
        return ShopReponsitory.findAll();
    }

    public Shop findById(int id) {
        Optional<Shop> Shop = ShopReponsitory.findById(id);
        return Shop.get();
    }

    public Shop createShop(Shop Shop) {
       return ShopReponsitory.save(Shop);
    }

    public Shop updateShop(Shop shop) {
        return ShopReponsitory.save(shop);
    }

    public void deleteShop(int id) {
        ShopReponsitory.deleteById(id);
    }

    public Boolean existsById(Integer id) {
        return ShopReponsitory.existsById(id) ? true : false;
    }
}
