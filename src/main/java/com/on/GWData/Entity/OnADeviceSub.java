package com.on.GWData.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="on_a_device_sub", catalog="pro_lamp")
public class OnADeviceSub {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column
    private Long deviceId;

    @Column(length = 50)
    private String sensorCode;

    @Column(length = 10)
    private String sensorName;

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

    public String getSensorCode() {
        return this.sensorCode;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return this.deviceId;
    }

    public void setSensorCode(String sensorCode) {
        this.sensorCode = sensorCode;
    }

    public String getSensorName() {
        return this.sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
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
