package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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
    private String image;
    private Boolean status;

    @OneToMany(mappedBy = "categoryNew")
    public List<CategoryItem> listCategory;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_account")
    private Account accountCreateCategory;
}
