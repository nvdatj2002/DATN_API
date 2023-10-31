package com.example.DATN_API.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Table(name = "category")

public class Category {
    @Id
    private int id;
    private String type_category;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    Date create_date = new Date();


    @OneToMany(mappedBy = "category")
    public List<CategoryItem> listCategory;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_account")
    private Account accountCreateCategory;
}
