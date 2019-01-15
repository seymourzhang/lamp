package com.on.wechat.repository;


import com.on.wechat.entity.WechatGoods;
import com.on.wechat.entity.WechatIndentCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WechatIndentCompletionRepository extends JpaRepository<WechatIndentCompletion, Long> {

    @Query(value = "select * from wechat_indent_completion where indent_id = :orderId", nativeQuery = true)
    WechatIndentCompletion findByIndentId(@Param("orderId") Long orderId);

}
