package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="wechat_user", catalog=  "pro_lamp",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"Id", "open_id"})
        })
public class WechatUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long Id;

    @Column(name = "open_id", length = 100)
    private String openId;

    @Column(name = "session_key", length = 200)
    private String sessionKey;

    @Column(name = "nick_name", length = 100)
    private String nickName;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "language", length = 50)
    private String language;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "province", length = 100)
    private String province;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "signature", length = 100)
    private String signature;

    @Column(name = "iv", length = 50)
    private String iv;

    @Column(name = "raw_data", length = 500)
    private String rawData;

    @Column(name = "code", length = 200)
    private String code;

    @Column(name = "local_session_id", length = 100)
    private String localSessionId;

    public Long getId() {
        return this.Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocalSessionId() {
        return localSessionId;
    }

    public void setLocalSessionId(String localSessionId) {
        this.localSessionId = localSessionId;
    }
}
