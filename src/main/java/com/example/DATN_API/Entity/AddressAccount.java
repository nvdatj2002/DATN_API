package com.example.DATN_API.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_account")
public class AddressAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private InfoAccount address_account;

    private String city;
    private String district;
    private String ward;
    private String address;
    private boolean status;
}
