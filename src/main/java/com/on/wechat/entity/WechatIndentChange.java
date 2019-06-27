package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_indent_change", catalog=  "pro_wechat_cherry")
public class WechatIndentChange implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "indent_id", length = 10)
    private Long indentId;

    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "money_sum", length = 100)
    private BigDecimal moneySum;

    @Column(name = "address_id", length = 10)
    private String addressId;

    @Column(name = "transport_indent", length = 50)
    private String transportIndent;

    /**
     * 注释:
     * 01:  待付款
     * 02:  待发货
     * 03:  待收货
     * 04:  待确认
     * 05:  交易成功
     * 06:  申请售后
     * 07:  取消交易
     */
    @Column(name = "operation_type", length = 10)
    private String operationType;

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

    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
        this.indentId = indentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getTransportIndent() {
        return transportIndent;
    }

    public void setTransportIndent(String transportIndent) {
        this.transportIndent = transportIndent;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
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
