package com.on.wechat.repository;

import com.on.wechat.entity.WechatShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatCartRepository extends JpaRepository<WechatShoppingCart, Long> {

    @Query(value = "select * from wechat_shopping_cart where user_id = :userId ", nativeQuery = true)
    List<WechatShoppingCart> findByUserId(@Param("userId") int userId);

    @Modifying
    @Query(value = "delete from wechat_shopping_cart where user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(@Param("userId") int userId);
}
