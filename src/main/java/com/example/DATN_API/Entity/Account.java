package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private Date create_date;
    public boolean status;

    @OneToMany(mappedBy = "accountCreateCategory")
    @JsonIgnore
    public List<Category> listCategory;

//    @JsonIgnore
//    @OneToMany(mappedBy = "account")
//    public List<CategoryItem> listCategoryItem;

    @OneToOne(mappedBy = "account")
    private Shop shop;

    @OneToMany(mappedBy = "account_like")
    private List<LikeProduct> likeProductes;

    @OneToMany(mappedBy = "accountOrder")
    @JsonIgnore
    private List<Order> orders;

}
