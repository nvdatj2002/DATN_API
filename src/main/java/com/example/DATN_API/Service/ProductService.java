package com.example.DATN_API.Service;
import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.ProductReponsitory;

@Service
public class ProductService {
	@Autowired
	ProductReponsitory productReponsitory;

	public List<ProductEntity> findAll() {
		return productReponsitory.findAll();
	}

	public ProductEntity findById(int id) {
		Optional<ProductEntity> product = productReponsitory.findById(id);
		return product.get();
	}
	
	public void createProduct(ProductEntity product) {
		productReponsitory.save(product);
	}
	
	public void updateProduct(int id,ProductEntity product) {
		product.setId(id);
		productReponsitory.save(product);
	}
	
	public void deleteProduct(int id) {
		productReponsitory.deleteById(id);
	}

	public Boolean existsById(Integer id) {
		return productReponsitory.existsById(id) ? true : false;
	}
}
