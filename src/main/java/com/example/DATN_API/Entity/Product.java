package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_shop")
    @JsonIgnore
    private Shop shop;

    private String product_name;

    @OneToMany(mappedBy = "product_image")
    private ImageProduct image_product;

    private double price;

    private double promotion_price;

    private Date create_date;

    private String description;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryItem category_product;

    @OneToOne(mappedBy = "product_like")
    private LikeProduct likeProduct;
}
