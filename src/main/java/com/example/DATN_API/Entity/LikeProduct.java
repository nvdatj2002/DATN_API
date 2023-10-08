package com.example.DATN_API.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "like_product")
public class LikeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_product")
    private Product product_like;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private Account account_like;
}
