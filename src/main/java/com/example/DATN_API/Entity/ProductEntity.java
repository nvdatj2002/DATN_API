package com.example.DATN_API.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "product", schema = "datn", catalog = "")
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_shop")
    private Integer idShop;
    @Basic
    @Column(name = "product_name")
    private String productName;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "promotion_price")
    private Double promotionPrice;
    @Basic
    @Column(name = "create_date")
    private Timestamp createDate;
    @Basic
    @Column(name = "start_promotion")
    private Timestamp startPromotion;
    @Basic
    @Column(name = "end_promotion")
    private Timestamp endPromotion;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "status")
    private Boolean status;
    @Basic
    @Column(name = "id_category_item")
    private Integer idCategoryItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdShop() {
        return idShop;
    }

    public void setIdShop(Integer idShop) {
        this.idShop = idShop;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getStartPromotion() {
        return startPromotion;
    }

    public void setStartPromotion(Timestamp startPromotion) {
        this.startPromotion = startPromotion;
    }

    public Timestamp getEndPromotion() {
        return endPromotion;
    }

    public void setEndPromotion(Timestamp endPromotion) {
        this.endPromotion = endPromotion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getIdCategoryItem() {
        return idCategoryItem;
    }

    public void setIdCategoryItem(Integer idCategoryItem) {
        this.idCategoryItem = idCategoryItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (id != that.id) return false;
        if (Double.compare(price, that.price) != 0) return false;
        if (idShop != null ? !idShop.equals(that.idShop) : that.idShop != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (promotionPrice != null ? !promotionPrice.equals(that.promotionPrice) : that.promotionPrice != null)
            return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (startPromotion != null ? !startPromotion.equals(that.startPromotion) : that.startPromotion != null)
            return false;
        if (endPromotion != null ? !endPromotion.equals(that.endPromotion) : that.endPromotion != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (idCategoryItem != null ? !idCategoryItem.equals(that.idCategoryItem) : that.idCategoryItem != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (idShop != null ? idShop.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (promotionPrice != null ? promotionPrice.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (startPromotion != null ? startPromotion.hashCode() : 0);
        result = 31 * result + (endPromotion != null ? endPromotion.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (idCategoryItem != null ? idCategoryItem.hashCode() : 0);
        return result;
    }
}
