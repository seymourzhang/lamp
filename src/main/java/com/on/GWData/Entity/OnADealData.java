package com.on.GWData.Entity;

import com.on.util.common.PubFun;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="on_a_deal_data", catalog=   "pro_lamp")
public class OnADealData {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dealDatetime")
    private Date dealDatetime;

    @Column(name = "imei", length = 100)
    private String imei;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "t1", length = 20)
    private String t1;

    @Column(name = "t2", length = 20)
    private String t2;

    @Column(name = "t3", length = 20)
    private String t3;

    @Column(name = "t4", length = 20)
    private String t4;

    @Column(name = "t5", length = 20)
    private String t5;

    @Column(name = "t6", length = 20)
    private String t6;

    @Column(name = "voltage", length = 20)
    private String voltage;

    @Column
    private Date createDateTime;

    @Column
    private Date modifyDateTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return this.t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return this.t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return this.t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return this.t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }

    public String getT5() {
        return this.t5;
    }

    public void setT5(String t5) {
        this.t5 = t5;
    }

    public String getT6() {
        return this.t6;
    }

    public void setT6(String t6) {
        this.t6 = t6;
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
