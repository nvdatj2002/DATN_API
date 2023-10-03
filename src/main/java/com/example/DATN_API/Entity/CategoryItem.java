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
@Table(name = "category_item")
public class CategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "type_category")
    @JsonIgnore
    private Category category;

    private String type_category_item;

    private Date create_date;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "category_product")
    private List<Product> products;
}
