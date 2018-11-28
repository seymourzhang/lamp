package com.on.wechat.service.Impl;

import com.on.wechat.entity.WechatAddress;
import com.on.wechat.entity.WechatUser;
import com.on.wechat.repository.WechatAddressRepository;
import com.on.wechat.repository.WechatUserRepository;
import com.on.wechat.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WechatUserServiceImpl implements WechatUserService{

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private WechatAddressRepository wechatAddressRepository;

    public WechatUser saveWechatUser(WechatUser wechatUser) {
        return wechatUserRepository.save(wechatUser);
    }

    public WechatUser findByOpenId(String openId) {
        return wechatUserRepository.findByOpenId(openId);
    }

    public List<WechatAddress> findByUserId(String userId) {
        return wechatAddressRepository.findByUserId(userId);
    }

    public WechatAddress addressSave(WechatAddress wa) {
        WechatAddress newOne = wechatAddressRepository.save(wa);
        if (newOne.getIsDef() == 1) {
            wechatAddressRepository.updateDefaultStatus(newOne.getId(), Long.parseLong(newOne.getUserId()));
        }
        return newOne;
    }

    public WechatAddress findById(Long id) {
        return wechatAddressRepository.findOne(id);
    }

    public void updateDefaultStatus(Long id, Long userId) {
        wechatAddressRepository.updateDefaultStatus(id, userId);
    }

    public WechatAddress findDef(String userId) {
        return wechatAddressRepository.findDef(userId);
    }

    public void addressDelete(Long id) {
        WechatAddress wa = wechatAddressRepository.findOne(id);
        wechatAddressRepository.delete(id);
        if (wa.getIsDef() == 1) {
            wechatAddressRepository.updateDefaultStatus(id, Long.parseLong(wa.getUserId()));
        }
    }
}
