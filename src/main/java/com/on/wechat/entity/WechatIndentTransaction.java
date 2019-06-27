package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_indent_transaction", catalog=  "pro_wechat_cherry")
public class WechatIndentTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "indent_code", length = 200)
    private String indentCode;

    @Column(name = "user_id", length = 100)
    private String userId;

    @Column(name = "address_id", length = 10)
    private String addressId;

    @Column(name = "money_sum", length = 100)
    private BigDecimal moneySum;

    @Column(name = "transport_indent", length = 50)
    private String transportIndent;

    @Column(name = "form_id", length = 50)
    private String formId;

    /**
     * 注释:
     * 01:  待付款
     * 02:  待发货
     * 03:  待收货
     * 04:  待确认
     */
    @Column(name = "operation_type", length = 10)
    private String operationType;

    @Column(name = "operation_date", length = 100)
    private Date operationDate;

    @Column(name = "modify_datetime", length = 100)
    private Date modifyDatetime;

    @Column(name = "create_datetime", length =  100)
    private Date createDatetime;

    public Long getId() {
        return this.Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getIndentCode() {
        return this.indentCode;
    }

    public void setIndentCode(String indentCode) {
        this.indentCode = indentCode;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return this.addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public BigDecimal getMoneySum() {
        return this.moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
    }

    public String getTransportIndent() {
        return transportIndent;
    }

    public void setTransportIndent(String transportIndent) {
        this.transportIndent = transportIndent;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getOperationType() {
        return this.operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Date getOperationDate() {
        return this.operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Date getModifyDatetime() {
        return this.modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
}
