package com.example.DATN_API.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double total;

    private Date create_date;

    @OneToOne
    @JoinColumn(name = "id_address_order")
    private AddressOrder address_order;

    @OneToMany(mappedBy = "status_order")
    private StatusOrder status;

    private boolean pay;

}
