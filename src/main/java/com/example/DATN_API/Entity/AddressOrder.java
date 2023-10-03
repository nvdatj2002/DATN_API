package com.example.DATN_API.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_order")
public class AddressOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
