package com.example.DATN_API.Entity;

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
@Table(name = "account")
public class Account {
    @Id
    private String username;
    private String password;
    private Date create_date;

    @OneToOne(mappedBy = "account")
    private InfoAccount info_account;
    private boolean status;


    @OneToOne(mappedBy = "create_by")
    List<Category> categories;

    @OneToMany(mappedBy = "account")
    List<CategoryItem> categoryItems;

    @OneToMany(mappedBy = "account_like")
    private List<LikeProduct> products_like;
}
