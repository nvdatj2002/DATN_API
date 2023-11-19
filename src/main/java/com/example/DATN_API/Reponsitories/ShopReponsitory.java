package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopReponsitory extends JpaRepository<Shop, Integer> {
    @Query("select s from Shop s join s.products p where p.id = ?1")
    public Shop findShopByProduct(int id_product);

    @Query("select s from Shop s where s.accountShop=?1")
    public Shop findShopByAccount(Account account);
}
