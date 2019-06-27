package com.on.mygirl.Entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
@Table(name="my_girl", catalog=  "pro_wechat_cherry")
public class MyGirl {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone_no", length = 20)
    private String phoneNo;

    @Column(name = "variety_id", length = 20)
    private String varietyId;

    @Column(name = "variety_name", length = 20)
    private String varietyName;

    @Column(name = "price", length = 20)
    private Double price;

    @Column(name = "num", length = 20)
    private Integer num;

    @Column(name = "trans_cast", length = 20)
    private Double transCast;

    @Column(name = "package_cast", length = 20)
    private Double packageCast;

    @Column(name = "incubator_cast", length = 20)
    private Double incubatorCast;

    @Column(name = "total_cast", length = 20)
    private Double totalCast;

    @Column(name = "sub_price", length = 20)
    private Double subPrice;

    @Column(name = "actual_in", length = 20)
    private Double actualIn;

    @Column(name = "actual_out", length = 20)
    private Double actualOut;

    @Column(name = "benefits", length = 20)
    private Double benefits;

    @Column(name = "deliver_date", length = 20)
    private Date deliverDate;

    @Column(name = "trans_no", length = 20)
    private String transNo;

    @Column(name = "origin_place", length = 20)
    private String originPlace;

    @Column(name = "tips", length = 5000)
    private String tips;

    @Column(name = "accept_date", length = 20)
    private Date acceptDate;

    @Column(name = "create_datetime", length = 20)
    private Date createDateTime;

    @Column(name = "modify_datetime", length = 20)
    private Date modifyDateTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(String varietyId) {
        this.varietyId = varietyId;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getTransCast() {
        return transCast;
    }

    public void setTransCast(Double transCast) {
        this.transCast = transCast;
    }

    public Double getPackageCast() {
        return packageCast;
    }

    public void setPackageCast(Double packageCast) {
        this.packageCast = packageCast;
    }

    public Double getIncubatorCast() {
        return incubatorCast;
    }

    public void setIncubatorCast(Double incubatorCast) {
        this.incubatorCast = incubatorCast;
    }

    public Double getTotalCast() {
        return totalCast;
    }

    public void setTotalCast(Double totalCast) {
        this.totalCast = totalCast;
    }

    public Double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(Double subPrice) {
        this.subPrice = subPrice;
    }

    public Double getActualIn() {
        return actualIn;
    }

    public void setActualIn(Double actualIn) {
        this.actualIn = actualIn;
    }

    public Double getActualOut() {
        return actualOut;
    }

    public void setActualOut(Double actualOut) {
        this.actualOut = actualOut;
    }

    public Double getBenefits() {
        return benefits;
    }

    public void setBenefits(Double benefits) {
        this.benefits = benefits;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Date getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getModifyDateTime() {
        return this.modifyDateTime;
    }

    public void setModifyDateTime(Date modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
    }
}
