package com.on.wechat.service.Impl;

import com.on.util.common.PageData;
import com.on.wechat.entity.WechatShoppingCart;
import com.on.wechat.repository.WechatCartRepository;
import com.on.wechat.service.WechatCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatCartServiceImpl implements WechatCartService {
    @Autowired
    private WechatCartRepository wechatCartRepository;

    public List<WechatShoppingCart> findByUserId(int userId) {
        return wechatCartRepository.findByUserId(userId);
    }

    public WechatShoppingCart save(WechatShoppingCart wechatShoppingCart) {
        return wechatCartRepository.save(wechatShoppingCart);
    }

    public WechatShoppingCart findById(Long cartId) {
        return wechatCartRepository.findOne(cartId);
    }

    public void delete(Long cartId) {
        wechatCartRepository.delete(cartId);
    }

    public void deleteByUserId(int userId) {
        wechatCartRepository.deleteAllByUserId(userId);
    }
}
