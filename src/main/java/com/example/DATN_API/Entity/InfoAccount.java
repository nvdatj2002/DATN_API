package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "info_account")
public class InfoAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private Account account;

    private String fullname;

    private String id_card;

    private String phone;

    private boolean gender;

    @OneToMany(mappedBy = "info_account")
    private AddressAccount address_account;

    private String email;

    private String image;

}
