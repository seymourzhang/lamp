package com.on.wechat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="wechat_goods_size", catalog=  "pro_wechat_cherry")
public class WechatGoodsSize implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "goods_id", length = 10)
    private Long goodsId;

    @Column(name = "goods_size", length = 100)
    private String goodsSize;

    @Column(name = "goods_size_amount", length = 100)
    private String goodsSizeAmount;

    @Column(name = "modify_datetime", length = 100)
    private Date modifyDatetime;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getGoodsSizeAmount() {
        return goodsSizeAmount;
    }

    public void setGoodsSizeAmount(String goodsSizeAmount) {
        this.goodsSizeAmount = goodsSizeAmount;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }
}
