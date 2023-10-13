package com.example.DATN_API.Service;



import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.LikeProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.LikeProductReponsitory;

@Service
public class LikeProductService {
	@Autowired
	LikeProductReponsitory LikeProductReponsitory;

	public List<LikeProductEntity> findAll() {
		return LikeProductReponsitory.findAll();
	}

	public LikeProductEntity findById(int id) {
		Optional<LikeProductEntity> LikeProduct = LikeProductReponsitory.findById(id);
		return LikeProduct.get();
	}
	
	public void createLikeProduct(LikeProductEntity LikeProduct) {
		LikeProductReponsitory.save(LikeProduct);
	}
	
	public void updateLikeProduct(int id,LikeProductEntity LikeProduct) {
		LikeProduct.setId(id);
		LikeProductReponsitory.save(LikeProduct);
	}
	
	public void deleteLikeProduct(int id) {
		LikeProductReponsitory.deleteById(id);
	}

	public Boolean existsById(Integer id) {
		return LikeProductReponsitory.existsById(id) ? true : false;
	}
}
