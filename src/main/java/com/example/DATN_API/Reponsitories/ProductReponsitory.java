package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Integer> {
	@Query("select p from Product p where p.status=?1")
	List<Product> getProductbyStatus(int status);

	@Query("SELECT p FROM Product p WHERE p.product_name LIKE %?1%"
			+ " AND CAST(p.categoryItem_product.id AS STRING) LIKE %?2%" + " AND CAST(p.status AS STRING) LIKE %?3%")
	List<Product> findByProductName(String keyword, String idCategoryItem, String status);

	@Query("SELECT p FROM Product p WHERE CAST(p.id AS STRING) LIKE %?1%"
			+ " AND CAST(p.categoryItem_product.id AS STRING) LIKE %?2%" + " AND CAST(p.status AS STRING) LIKE %?3%")
	List<Product> findByKey(String keyword, String idCategoryItem, String status);
}