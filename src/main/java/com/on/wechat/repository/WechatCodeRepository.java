package com.on.wechat.repository;

import com.on.wechat.entity.WechatCode;
import com.on.wechat.entity.WechatShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WechatCodeRepository extends JpaRepository<WechatCode, Long> {

    @Query(value = "select * from wechat_code " +
                   "where we_code_type = :codeType ", nativeQuery = true)
    List<WechatCode> findByCodeType(@Param("codeType") String codeType);

}
