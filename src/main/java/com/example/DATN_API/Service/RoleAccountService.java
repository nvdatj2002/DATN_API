package com.example.DATN_API.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Entity.RoleAccount;
import com.example.DATN_API.Reponsitories.RoleAccountReponsitory;

@Service
public class RoleAccountService {
    @Autowired
    RoleAccountReponsitory roleAccountReponsitory;

    public RoleAccount createRoleAccount(RoleAccount role){
        try {
            return roleAccountReponsitory.save(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
