package com.example.DATN_API.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name = "username")
    private Account account;

    private String image;

    @OneToOne(mappedBy = "shop")
    private AddressShop addressShop;
}
