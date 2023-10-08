package com.example.DATN_API.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_product")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "create_by")
    private Account account_comment;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product_comment;

    @OneToMany(mappedBy = "comment")
    private List<CommentDetail> comment_details;
}
