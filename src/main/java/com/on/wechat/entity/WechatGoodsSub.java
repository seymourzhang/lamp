package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="wechat_goods_sub", catalog=  "pro_wechat_cherry")
public class WechatGoodsSub implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "goods_id", length = 10)
    private Long goodsId;

    @Column(name = "thumb_url_subs", length = 100)
    private String thumbUrlSub;

    @Column(name = "modify_datetime", length = 100)
    private Date modifyDatetime;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbUrlSub() {
        return thumbUrlSub;
    }

    public void setThumbUrlSub(String thumbUrlSub) {
        this.thumbUrlSub = thumbUrlSub;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }
}
