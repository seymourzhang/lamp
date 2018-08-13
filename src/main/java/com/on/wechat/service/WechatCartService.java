package com.on.wechat.service;

import com.on.wechat.entity.WechatShoppingCart;

import java.util.List;

public interface WechatCartService {

    List<WechatShoppingCart> findByUserId(int userId);

    WechatShoppingCart save(WechatShoppingCart wechatShoppingCart);

    WechatShoppingCart findById(Long cartId);

    void delete(Long cartId);

    void deleteByUserId(int userId);
}
