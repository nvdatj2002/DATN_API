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
    private String username;
    private String password;
    private Date create_date;

    @OneToOne(mappedBy = "account")
    private InfoAccount info_account;
    private boolean status;

    @OneToMany(mappedBy = "account_like")
    private List<LikeProduct> products_like;

    @JsonIgnore
    @OneToMany(mappedBy = "account_rate")
    private List<Rate> rates;

    @JsonIgnore
    @OneToMany(mappedBy = "account_comment")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "account_comment_details")
    private List<CommentDetail> comment_details;

    @OneToMany(mappedBy = "account_role")
    private List<RoleAccount> role;
}
