package com.example.DATN_API.Reponsitories;

import com.example.DATN_API.Entity.OrderCustomer;
import com.example.DATN_API.Entity.OrderDetail;
import com.example.DATN_API.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Integer> {
    @Query("Select o FROM OrderDetail o WHERE o.orders.id = ?1")
    List<OrderDetail> findByIdOrder(int idOrder);

    @Query("Select o, odt, p FROM OrderCustomer o"
            + " JOIN o.orderDetails odt JOIN o.status st"
            + " JOIN odt.productOrder p JOIN p.shop s WHERE s.id = ?1")
    List<Object[]> getTotalByMonth(int idShop);
}