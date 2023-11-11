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

    @Query(value =
            "SELECT TOP 10 " +
                    "p.id AS idProduct, " +
                    "p.id_Shop AS idShop, " +
                    "p.product_Name AS productName, " +
                    "p.price AS price, " +
                    "p.create_Date AS createDate, " +
                    "(SELECT ip.url FROM Image_Product ip WHERE ip.id_Product = p.id FOR JSON AUTO) AS imageUrls " +
                    "FROM Product p " +
                    "LEFT JOIN Like_Product lp ON p.id = lp.id_Product " +
                    "ORDER BY p.create_Date DESC", nativeQuery = true)
    List<Object[]> getTop10Products();


//    @Query("SELECT p FROM Product p WHERE p.id = :keyword"
//            + " AND (CAST(p.categoryItem_product.id AS string) = :idCategoryItem OR :idCategoryItem = '')"
//            + " AND (CAST(p.status AS string) = :status OR :status = '')")
//    List<Product> findByKey(int keyword, int minQuantity, int maxQuantity, String idCategoryItem, String status);
//
//    @Query("SELECT p FROM Product p WHERE p.product_name LIKE %:keyword%"
//            + " AND (CAST(p.categoryItem_product.id AS string) = :idCategoryItem OR :idCategoryItem = '')"
//            + " AND (CAST(p.status AS string) = :status OR :status = '')")
//    List<Product> findByProductName(String keyword, int minQuantity, int maxQuantity, String idCategoryItem,
//                                    String status);
}