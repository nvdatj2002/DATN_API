package com.example.DATN_API.Service;

import com.example.DATN_API.Entity.InfoAccount;
import com.example.DATN_API.Reponsitories.InfoAccountReponsitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoAccountService {
	@Autowired
	InfoAccountReponsitory infoAccountReponsitory;

	public InfoAccount findByEmail(String email) {
		try {
			return infoAccountReponsitory.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
