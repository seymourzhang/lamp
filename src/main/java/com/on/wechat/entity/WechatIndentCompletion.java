package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_indent_completion", catalog=  "pro_lamp")
public class WechatIndentCompletion implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "indent_id", length = 10)
    private String indentId;

    @Column(name = "indent_code", length = 100)
    private String indentCode;

    @Column(name = "money_sum", length = 100)
    private BigDecimal moneySum;

    @Column(name = "address_id", length = 10)
    private String addressId;

    @Column(name = "transport_indent", length = 50)
    private String transportIndent;

    @Column(name = "recall_message", length = 5000)
    private String recallMessage;

    /**
     * 注释:
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndentId() {
        return this.indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getIndentCode() {
        return this.indentCode;
    }

    public void setIndentCode(String indentCode) {
        this.indentCode = indentCode;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public void setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
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

    public String getRecallMessage() {
        return recallMessage;
    }

    public void setRecallMessage(String recallMessage) {
        this.recallMessage = recallMessage;
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
