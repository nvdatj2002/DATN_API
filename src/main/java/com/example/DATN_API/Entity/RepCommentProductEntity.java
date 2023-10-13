package com.example.DATN_API.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "rep_comment_product", schema = "datn", catalog = "")
public class RepCommentProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_comment")
    private Integer idComment;
    @Basic
    @Column(name = "id_account")
    private Integer idAccount;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepCommentProductEntity that = (RepCommentProductEntity) o;

        if (id != that.id) return false;
        if (idComment != null ? !idComment.equals(that.idComment) : that.idComment != null) return false;
        if (idAccount != null ? !idAccount.equals(that.idAccount) : that.idAccount != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idComment != null ? idComment.hashCode() : 0);
        result = 31 * result + (idAccount != null ? idAccount.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}
