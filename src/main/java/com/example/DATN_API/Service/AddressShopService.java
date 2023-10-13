package com.example.DATN_API.Service;



import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.ShopAddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.AddressShopReponsitory;

@Service
public class AddressShopService {
	@Autowired
	AddressShopReponsitory AddressShopReponsitory;

	public List<ShopAddressEntity> findAll() {
		return AddressShopReponsitory.findAll();
	}

	public ShopAddressEntity findById(int id) {
		Optional<ShopAddressEntity> AddressShop = AddressShopReponsitory.findById(id);
		return AddressShop.get();
	}
	
	public void createAddressShop(ShopAddressEntity AddressShop) {
		AddressShopReponsitory.save(AddressShop);
	}
	
	public void updateAddressShop(int id,ShopAddressEntity AddressShop) {
		AddressShop.setId(id);
		AddressShopReponsitory.save(AddressShop);
	}
	
	public void deleteAddressShop(int id) {
		AddressShopReponsitory.deleteById(id);
	}

	public Boolean existsById(Integer id) {
		return AddressShopReponsitory.existsById(id) ? true : false;
	}
}
