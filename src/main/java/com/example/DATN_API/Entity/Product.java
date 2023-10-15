package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_shop")
    private Shop shop;

    private String product_name;

    @OneToMany(mappedBy = "product_image")
    @JsonIgnore
    private List<ImageProduct> image_product;

    private double price;

    private double promotion_price;

    private Date create_date;
    private Date start_promotion;
    private Date end_promotion;
    private String description;

    private boolean status;

    @OneToOne(mappedBy = "product")
    private DetailProduct detailProducts;
//
    @ManyToOne
    @JoinColumn(name = "id_category_item")
    @JsonIgnore
    private CategoryItem category_product;
//
    @OneToOne(mappedBy = "product_like")
    private LikeProduct likeProduct;

    @OneToOne(mappedBy = "productOrder")
    @JsonIgnore
    private OrderDetail orderDetail;

}
