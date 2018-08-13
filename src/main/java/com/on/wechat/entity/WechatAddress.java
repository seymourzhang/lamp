package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_address", catalog=  "pro_lamp")
public class WechatAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "user_id", length = 100, columnDefinition = "varchar(10) not null")
    private String userId;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "gender", length = 100)
    private String gender;

    @Column(name = "tel", length = 100)
    private String tel;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "is_def", length = 10)
    private int isDef;

    @Column(name = "operation_date", length = 100, columnDefinition = "varchar(50) not null")
    private Date operationDate;

    @Column(name = "modify_datetime", length = 100, columnDefinition = "varchar(50) not null")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDef() {
        return isDef;
    }

    public void setIsDef(int isDef) {
        this.isDef = isDef;
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
