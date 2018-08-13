package com.on.wechat.repository;

import com.on.wechat.entity.WechatAddress;
import com.on.wechat.entity.WechatShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WechatAddressRepository extends JpaRepository<WechatAddress, Long> {

    @Query(value = "select * from wechat_address where user_id = :userId ", nativeQuery = true)
    List<WechatAddress> findByUserId(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(value = "update wechat_address set is_def = (" +
            "case when id = :id then 1 else 0 end" +
            ") where user_id = :userId", nativeQuery = true)
    void updateDefaultStatus(@Param("id") Long id, @Param("userId") Long userId);


    @Query(value = "select w from WechatAddress w where w.isDef = 1")
    WechatAddress findDef();
}
