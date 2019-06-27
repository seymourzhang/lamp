package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="wechat_user_share", catalog="pro_wechat_cherry",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"Id", "open_group_id"})
        })
public class WechatUserShare implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "sharing_user_id", length = 10)
    private Long sharingUserId;

    @Column(name = "attracted_user_id")
    private Long attractedUserId;

    @Column(name = "open_group_id", length = 100)
    private String openGroupId;

    @Column(name = "create_date", length = 200)
    private Date createDate;

    @Column(name = "create_time", length = 100)
    private Date createTime;

    @Column(name = "modify_date", length = 200)
    private Date modifyDate;

    @Column(name = "modify_time", length = 100)
    private Date modifyTime;

    public Long getId() {
        return this.Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public Long getSharingUserId() {
        return sharingUserId;
    }

    public void setSharingUserId(Long sharingUserId) {
        this.sharingUserId = sharingUserId;
    }

    public Long getAttractedUserId() {
        return attractedUserId;
    }

    public void setAttractedUserId(Long attractedUserId) {
        this.attractedUserId = attractedUserId;
    }

    public String getOpenGroupId() {
        return openGroupId;
    }

    public void setOpenGroupId(String openGroupId) {
        this.openGroupId = openGroupId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
