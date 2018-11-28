package com.on.wechat.action;

import com.alibaba.fastjson.JSONObject;
import com.on.util.action.BaseAction;
import com.on.util.common.*;
import com.on.wechat.entity.WechatAddress;
import com.on.wechat.entity.WechatUser;
import com.on.wechat.service.WechatUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@SpringBootApplication
@RequestMapping("wechat")
public class WechatUserController extends BaseAction {

    @Autowired
    private WechatUserService wechatUserService;

    private int limit = 10;


    @RequestMapping("/signIn")
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        WechatUser we = new WechatUser();
        String ni = pd.get("nick_name").toString();
        System.out.println("nick_name" + ni);
        String nickName = Base64.encodeToString(ni.getBytes());
        System.out.println("nick_name" + Base64.decodeToString(nickName));
        String sessionKey = pd.get("session_key").toString();
        String openId = pd.get("open_id").toString();
        String idStr = pd.get("id").toString();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("sessionLocal", new SimpleHash("SHA-1", sessionKey, openId));
//        session.setTimeout(60 * 1000 * 5);
        String rawData = Base64.encodeToString(pd.get("raw_data").toString().getBytes());
        if (!"".equals(idStr)) {
            we.setId(Long.parseLong(idStr));
        }
        we.setNickName(nickName);
        we.setGender(pd.get("gender").toString());
        we.setLanguage(pd.get("language").toString());
        we.setCity(pd.get("city").toString());
        we.setProvince(pd.get("province").toString());
        we.setCountry(pd.get("country").toString());
        we.setAvatarUrl(pd.get("avatar_url").toString());
        we.setOpenId(openId);
        we.setSessionKey(sessionKey);
        we.setSignature(pd.get("signature").toString());
        we.setIv(pd.get("iv").toString());
        we.setRawData(rawData);
        we.setCode(pd.get("code").toString());

        WechatUser wCheck = wechatUserService.findByOpenId(openId);
        if (null == wCheck) {
            we = wechatUserService.saveWechatUser(we);
        } else {
            we.setLocalSessionId(session.getId().toString());
            we.setId(wCheck.getId());
            we = wechatUserService.saveWechatUser(we);
        }

        we.setNickName(Base64.decodeToString(we.getNickName()));
        we.setRawData(Base64.decodeToString(we.getRawData()));
        json.put("obj", we);
        json.put("sessionId", session.getId());
        super.writeJson(json, response);
    }

    @RequestMapping("/addressQuery")
    public void addressQuery(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        JSONObject paginate = new JSONObject();
        String userId = pd.getString("user_id");
        List<WechatAddress> lwa = wechatUserService.findByUserId(userId);
        List<PageData> tempL = new ArrayList<>();
        int temp = 0;
        if (this.limit < lwa.size()) {
            temp = this.limit;
        } else {
            temp = lwa.size();
        }
        for (int i = 0; i < temp; i++) {
            WechatAddress wa = lwa.get(i);
            PageData a = new PageData();
            a.put("id", wa.getId());
            a.put("name", wa.getName());
            a.put("gender", wa.getGender());
            a.put("tel", wa.getTel());
            a.put("isDef", wa.getIsDef());
            a.put("address", wa.getAddress());
            tempL.add(a);
        }
        if (lwa.size() > this.limit) {
            paginate.put("hasNext", true);
            paginate.put("next", lwa.get(this.limit + 1));
            this.limit += pd.getInteger("limit");
        } else {
            this.limit = 5;
        }
        paginate.put("perPage", "5");
        paginate.put("total", lwa.size());
        json.put("paginate", paginate);
        json.put("obj", tempL);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': ''}"));
        super.writeJson(json, response);
    }

    @RequestMapping("/addressSave")
    public void addressSave(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        String addressId = pd.getString("address_id");
        String userId = pd.getString("user_id");
        String name = pd.getString("name");
        String gender = pd.getString("gender");
        String tel = pd.getString("tel");
        int isDef = pd.getInteger("is_def");
        String address = pd.getString("address");
        WechatAddress wechatAddress = new WechatAddress();
        if (!"".equals(addressId)) {
            wechatAddress.setId(Long.parseLong(addressId));
        }
        wechatAddress.setUserId(userId);
        wechatAddress.setName(name);
        wechatAddress.setAddress(address);
        wechatAddress.setTel(tel);
        wechatAddress.setIsDef(isDef);
        wechatAddress.setGender(gender);
        wechatAddress.setModifyDatetime(new Date());
        wechatAddress.setOperationDate(new Date());
        WechatAddress lwa = wechatUserService.addressSave(wechatAddress);
        if (lwa != null) {
            json.put("obj", lwa);
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '保存成功'}"));
        } else {
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '保存失败'}"));
        }
        super.writeJson(json, response);
    }

    @RequestMapping("/addressSetDefault")
    public void addressSetDefault(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        Long addressId = Long.parseLong(pd.getString("id"));
        Long userId = Long.parseLong(pd.getString("user_id"));
        WechatAddress wa = wechatUserService.findById(addressId);
        if (wa != null) {
            wechatUserService.updateDefaultStatus(addressId, userId);
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        } else {
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '数据错误'}"));
        }
        super.writeJson(json, response);
    }

    @RequestMapping("/addressDetail")
    public void addressDetail(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        Long addressId = Long.parseLong(pd.getString("addressId"));
        WechatAddress wa = wechatUserService.findById(addressId);
        if (wa != null) {
            json.put("data", wa);
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        } else {
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '数据错误'}"));
        }
        super.writeJson(json, response);
    }

    @RequestMapping("/addressQueryDef")
    public void addressQueryDef(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        String userId = pd.getString("userId");
        WechatAddress wa = wechatUserService.findDef(userId);
        if (wa != null) {
            json.put("data", wa);
            json.put("meta", JSONObject.parseObject("{'code': '0','message': ''}"));
        } else {
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '暂无数据'}"));
        }
        super.writeJson(json, response);
    }

    @RequestMapping("/addressDelete")
    public void addressDelete(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        Long addressId = Long.parseLong(pd.getString("id"));
        wechatUserService.addressDelete(addressId);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        super.writeJson(json, response);
    }

}


