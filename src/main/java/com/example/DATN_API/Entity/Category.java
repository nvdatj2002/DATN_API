package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type_category;

    private Date create_date;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @JsonIgnore
    private Account create_by;

    @OneToMany(mappedBy = "category")
    private CategoryItem categoryItems;
}
