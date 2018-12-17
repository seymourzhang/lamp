package com.on.wechat.service;

import com.on.util.common.PageData;
import com.on.wechat.entity.WechatShoppingCart;

import java.util.HashMap;
import java.util.List;

public interface WechatCartService {

    List<HashMap<String, Object>> findByUserId(PageData pd);

    List<WechatShoppingCart> findByUserIdAndType(int userId, String cType);

    WechatShoppingCart save(WechatShoppingCart wechatShoppingCart);

    WechatShoppingCart findById(Long cartId);

    void delete(Long cartId);

    void deleteByUserId(int userId);
}
