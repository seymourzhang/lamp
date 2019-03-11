package com.on.wechat.repository;

import com.on.wechat.entity.WechatUser;
import com.on.wechat.entity.WechatUserShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserShareRepository extends JpaRepository<WechatUserShare, Long> {


}
