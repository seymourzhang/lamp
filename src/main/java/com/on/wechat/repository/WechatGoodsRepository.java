package com.on.wechat.repository;

import com.on.wechat.entity.WechatGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatGoodsRepository extends JpaRepository<WechatGoods, Long> {

    @Query(value = "select * from wechat_goods where id = :goodsId", nativeQuery = true)
    List<WechatGoods> findById(@Param("goodsId") int goodsId);

}
