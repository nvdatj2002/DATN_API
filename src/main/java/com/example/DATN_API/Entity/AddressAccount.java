package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @JsonIgnore
    @JoinColumn(name = "id_account")
    private Account Addressaccount;
    private String city;
    private String district;
    private String ward;
    private String address;
    private boolean status;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "address")
    @JsonIgnore
    List<Order> orders;
}
