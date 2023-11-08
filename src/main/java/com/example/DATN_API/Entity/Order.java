package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double total;

    private Date create_date;

    private String address;

    @OneToMany(mappedBy = "order")
    private List<StatusOrder> status;

    private boolean pay;
//@JsonManagedReference
//    @OneToMany(mappedBy = "orders")
//    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "id_account")
    @JsonIgnore
    private Account accountOrder;
}