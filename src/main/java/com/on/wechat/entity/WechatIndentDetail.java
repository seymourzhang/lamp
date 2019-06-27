package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_indent_detail", catalog=  "pro_wechat_cherry")
public class WechatIndentDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "indent_id", length = 10)
    private String indentId;

    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "car_id", length = 10)
    private String carId;

    @Column(name = "good_id", length = 10)
    private String goodId;

    @Column(name = "good_price", length = 10)
    private String goodPrice;

    @Column(name = "good_amount", length = 10)
    private String goodAmount;

    @Column(name = "good_name", length = 30)
    private String goodName;

    @Column(name = "money_sum", length = 100)
    private BigDecimal moneySum;

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

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodAmount() {
        return goodAmount;
    }

    public void setGoodAmount(String goodAmount) {
        this.goodAmount = goodAmount;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
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
