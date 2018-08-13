package com.on.wechat.repository;

import com.on.wechat.entity.WechatAddress;
import com.on.wechat.entity.WechatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserRepository extends JpaRepository<WechatUser, Long> {

    @Query(value = "select w.* from wechat_user w where open_id = :openId", nativeQuery = true)
    WechatUser findByOpenId(@Param("openId") String openId);

}
