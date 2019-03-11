package com.on.wechat.service;

import com.on.wechat.entity.WechatAddress;
import com.on.wechat.entity.WechatUser;
import com.on.wechat.entity.WechatUserShare;

import java.util.List;

public interface WechatUserService {

    WechatUser saveWechatUser(WechatUser wechatUser);

    WechatUser findByOpenId(String openId);

    List<WechatAddress> findByUserId(String userId);

    WechatAddress addressSave(WechatAddress wa);

    WechatAddress findById(Long id);

    void updateDefaultStatus(Long id, Long userId);

    WechatAddress findDef(String userId);

    void addressDelete(Long id);

    WechatUserShare dealUserShareInfo(WechatUserShare wechatUserShare);
}
