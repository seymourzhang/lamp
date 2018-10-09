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

    @Query(value = "select * from wechat_address where user_id = :userId ORDER BY id DESC ", nativeQuery = true)
    List<WechatAddress> findByUserId(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(value = "update wechat_address c " +
            "           inner join (select if(exists(select 1 from wechat_address a where a.id = :id), :id, (select max(b.id) from wechat_address b where b.user_id = :userId)) check_id) d " +
            "       set c.is_def = ( " +
            "            case when c.id = d.check_id then 1 else 0 end " +
            "            ) where c.user_id = :userId", nativeQuery = true)
    void updateDefaultStatus(@Param("id") Long id, @Param("userId") Long userId);


    @Query(value = "select w from WechatAddress w where w.isDef = 1")
    WechatAddress findDef();
}
