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
		Product productold=findById(id);
		product.setId(id);
		product.setShop(productold.getShop());
		product.setCreate_date(productold.getCreate_date());
		product.setStart_promotion(productold.getStart_promotion());
		product.setEnd_promotion(productold.getEnd_promotion());
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

//	public List<Product> findByKey(int keyword, int minQuantity, int maxQuantity, String idCategoryItem,
//								   String status) {
//		return productReponsitory.findByKey(keyword, minQuantity, maxQuantity, idCategoryItem, status);
//	}
//
//	public List<Product> findByProductName(String keyword, int minQuantity, int maxQuantity, String idCategoryItem,
//										   String status) {
//		return productReponsitory.findByProductName(keyword, minQuantity, maxQuantity, idCategoryItem, status);
//	}
	
	 public Product getProductById(int productId) {
	        Optional<Product> optionalProduct = productReponsitory.findById(productId);
	        
	        if (optionalProduct.isPresent()) {
	            return optionalProduct.get();
	        } else {
	            // Xử lý trường hợp không tìm thấy sản phẩm (nếu cần)
	            return null;
	        }
	    }
}
