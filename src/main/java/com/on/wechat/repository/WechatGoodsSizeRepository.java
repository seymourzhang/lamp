package com.on.wechat.repository;

import com.on.wechat.entity.WechatGoods;
import com.on.wechat.entity.WechatGoodsSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatGoodsSizeRepository extends JpaRepository<WechatGoodsSize, Long> {

}
