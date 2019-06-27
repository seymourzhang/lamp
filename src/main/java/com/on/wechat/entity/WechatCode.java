package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_code", catalog="pro_wechat_cherry")
public class WechatCode implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "we_code_type", length = 100)
    private String weCodeType;

    @Column(name = "we_code_id", length = 100)
    private String weCodeId;

    @Column(name = "we_code_name", length = 500)
    private String weCodeName;

    @Column(name = "we_bak1")
    private BigDecimal weBak1;

    @Column(name = "we_bak2")
    private BigDecimal weBak2;

    @Column(name = "we_bak3")
    private String weBak3;

    @Column(name = "we_bak4")
    private String weBak4;

    @Column(name = "create_datetime", length = 100)
    private Date createDatetime;

    @Column(name = "modify_datetime", length = 100)
    private Date modifyDatetime;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getWeCodeType() {
        return weCodeType;
    }

    public void setWeCodeType(String weCodeType) {
        this.weCodeType = weCodeType;
    }

    public String getWeCodeId() {
        return weCodeId;
    }

    public void setWeCodeId(String weCodeId) {
        this.weCodeId = weCodeId;
    }

    public String getWeCodeName() {
        return weCodeName;
    }

    public void setWeCodeName(String weCodeName) {
        this.weCodeName = weCodeName;
    }

    public BigDecimal getWeBak1() {
        return weBak1;
    }

    public void setWeBak1(BigDecimal weBak1) {
        this.weBak1 = weBak1;
    }

    public BigDecimal getWeBak2() {
        return weBak2;
    }

    public void setWeBak2(BigDecimal weBak2) {
        this.weBak2 = weBak2;
    }

    public String getWeBak3() {
        return weBak3;
    }

    public void setWeBak3(String weBak3) {
        this.weBak3 = weBak3;
    }

    public String getWeBak4() {
        return weBak4;
    }

    public void setWeBak4(String weBak4) {
        this.weBak4 = weBak4;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }
}
