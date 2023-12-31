package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopReponsitory extends JpaRepository<Shop, Integer> {
    @Query("select s from Shop s join s.products p where p.id = ?1")
    public Shop findShopByProduct(int id_product);

    @Query("select s from Shop s join s.products p where p.status = ?1")
    public Page<Shop> findShopByStatusProduct(int id, Pageable pageable);

    @Query("select s from Shop s join s.products p where p.status = ?1 and p.id = ?2")
    public List<Shop> findShopSearchByIdProduct(int status, String idProduct);

    @Query("select s from Shop s join s.products p where p.status = ?1 and p.product_name like ?2")
    public Page<Shop> findShopSearchByNameProduct(int status, String name_product, Pageable pageable);
    @Query("select s from Shop s join s.products p where p.status = ?1 and s.shop_name like ?2")
    public List<Shop> findByName(int status, String shop_name);
    @Query("select s from Shop s join s.products p where p.status = ?1 and s.shop_name like ?2")
    public Page<Shop> findShopSearchByName(int status, String name, Pageable pageable);

}
