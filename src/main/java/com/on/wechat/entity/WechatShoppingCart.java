package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_shopping_cart", catalog=  "pro_lamp",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"Id", "user_id"})
        })
public class WechatShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "goods_id", length = 200)
    private String goodsId;

    @Column(name = "goods_price", length = 100)
    private BigDecimal goodsPrice;

    @Column(name = "goods_name", length = 100)
    private String goodsName;

    @Column(name = "thumb_url", length = 100)
    private String thumbUrl;

    @Column(name = "amount", length = 10)
    private Long amount;

    @Column(name = "total", length = 50)
    private BigDecimal total;

    @Column(name = "total_amount", length = 100)
    private BigDecimal totalAmount;

    @Column(name = "operation_date", length = 100)
    private Date operationDate;

    @Column(name = "modify_datetime", length = 100)
    private Date modifyDatetime;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }
}
