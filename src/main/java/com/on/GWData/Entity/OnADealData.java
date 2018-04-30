package com.on.GWData.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="on_a_deal_data", catalog="pro_lamp")
public class OnADealData {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column
    private Date dealDatetime;

    @Column(length = 100)
    private String imei;

    @Column
    private String uuid;

    @Column(length = 20)
    private String T1;

    @Column(length = 20)
    private String T2;

    @Column(length = 20)
    private String T3;

    @Column(length = 20)
    private String T4;

    @Column(length = 20)
    private String T5;

    @Column(length = 20)
    private String T6;

    @Column(length = 20)
    private String voltage;

    @Column
    private Date createDateTime;

    @Column
    private Date modifyDateTime;

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Date getCollectionDatetime () {
        return this.dealDatetime;
    }

    public void setCollectionDatetime(Date collectionDatetime) {
        this.dealDatetime = collectionDatetime;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getT1() {
        return this.T1;
    }

    public void setT1(String T1) {
        this.T1 = T1;
    }

    public String getT2() {
        return this.T2;
    }

    public void setT2(String T2) {
        this.T2 = T2;
    }

    public String getT3() {
        return this.T3;
    }

    public void setT3(String T3) {
        this.T3 = T3;
    }

    public String getT4() {
        return this.T4;
    }

    public void setT4(String T4) {
        this.T4 = T4;
    }

    public String getT5() {
        return this.T5;
    }

    public void setT5(String T5) {
        this.T5 = T5;
    }

    public String getT6() {
        return this.T6;
    }

    public void setT6(String T6) {
        this.T6 = T6;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
