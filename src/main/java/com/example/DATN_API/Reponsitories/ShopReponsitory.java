package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopReponsitory extends JpaRepository<Shop, Integer> {
	@Query(value = "SELECT * FROM shop where id_account = ?1", nativeQuery = true)
	Shop findByID_Account(Integer id_account);
}
