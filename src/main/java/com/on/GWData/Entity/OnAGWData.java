package com.on.GWData.Entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="on_a_gw_data", catalog="pro_lamp")
public class OnAGWData {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column
    private Date collectionDatetime;

    @Column(length = 100)
    private String imei;

    @Column(length = 5000)
    private String data;

    @Column
    private String uuid;

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
        return this.collectionDatetime;
    }

    public void setCollectionDatetime(Date collectionDatetime) {
        this.collectionDatetime = collectionDatetime;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
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
