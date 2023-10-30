package com.example.DATN_API.Service;
import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.ProductReponsitory;
@Service
public class ProductService {
	@Autowired
	ProductReponsitory productReponsitory;

	public List<Product> findAll() {
		return productReponsitory.findAll();
	}

	public List<Product> findProductbyStatus(int status) {
		return productReponsitory.getProductbyStatus(status);
	}


	public Product findById(int id) {
		Optional<Product> product = productReponsitory.findById(id);
		return product.get();
	}
	
	public Product createProduct(Product product) {
		try {
			Product productsave= productReponsitory.save(product);
			return productsave;
		}catch (Exception e) {
			e.printStackTrace();
			LogError.saveToLog(e);
		}
		return null;
	}
	
	public Product updateProduct(int id,Product product) {
		product.setId(id);
		try {
			Product productsave= productReponsitory.save(product);
			return productsave;
		}catch (Exception e){
			e.printStackTrace();
			LogError.saveToLog(e);
		}
		return null;
	}
	
	public void deleteProduct(int id) {
		productReponsitory.deleteById(id);
	}

	public Boolean existsById(Integer id) {
		return productReponsitory.existsById(id) ? true : false;
	}
}
