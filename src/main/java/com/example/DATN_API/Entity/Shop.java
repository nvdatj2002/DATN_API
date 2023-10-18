package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Shop")

public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String shop_name;

    @OneToOne()
    @JsonIgnore
    @JoinColumn(name = "id_account")
    private Account account;

    private String image;

    @OneToOne(mappedBy = "shopAddress")
    private AddressShop addressShop;

    @OneToMany(mappedBy = "shop")
    @JsonIgnore
    private List<Product> products;
}
