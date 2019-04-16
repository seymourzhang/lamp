package com.on.wechat.action;

import com.alibaba.fastjson.JSONObject;
import com.on.util.action.BaseAction;
import com.on.util.common.*;
import com.on.wechat.entity.WechatAddress;
import com.on.wechat.entity.WechatUser;
import com.on.wechat.entity.WechatUserShare;
import com.on.wechat.service.WechatIndentService;
import com.on.wechat.service.WechatUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.*;

@RestController
@SpringBootApplication
@RequestMapping("wechat")
public class WechatUserController extends BaseAction {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatIndentService wechatIndentService;

    private int limit = 10;


    @RequestMapping("/signIn")
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        WechatUser we = new WechatUser();
        String ni = pd.getString("nick_name");
        System.out.println("nick_name" + ni);
        String nickName = Base64.encodeToString(ni.getBytes());
        System.out.println("nick_name" + Base64.decodeToString(nickName));
        String idStr = pd.getString("id");
        String jsCode = pd.getString("js_code");
        String signInMode = pd.getString("signInMode");
        String openGId = pd.getString("openGId");

        JSONObject params = new JSONObject();
        params.put("appid", PubFun.getPropertyValue("Pub.APPLYID"));
        params.put("secret", PubFun.getPropertyValue("Pub.screte"));
        params.put("js_code", jsCode);
        params.put("grant_type", "authorization_code");
        StringBuilder parm = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				parm.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
        JSONObject loginResult = PubFun.get("https://api.weixin.qq.com/sns/jscode2session?" + parm.toString());
        String openId = loginResult.getString("openid");
        String sessionKey = loginResult.getString("session_key");

        if (openId != null) {
            Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.setAttribute("sessionLocal", new SimpleHash("SHA-1", sessionKey, openId));
//        session.setTimeout(60 * 1000 * 5);
            String rawData = Base64.encodeToString(pd.getString("raw_data").getBytes());
            if (!"".equals(idStr)) {
                we.setId(Long.parseLong(idStr));
            }
            WechatUser wCheck = wechatUserService.findByOpenId(openId);
            if (null != wCheck) {
                we = wCheck;
            } else {
                we.setUserType("01");
                we.setOpenId(openId);
            }
            if ("".equals(signInMode)) {
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
            } else {
                we.setSessionKey(sessionKey);
            }
            if (null == wCheck) {
                wCheck = wechatUserService.saveWechatUser(we);
            } else {
                we.setLocalSessionId(session.getId().toString());
                wCheck = wechatUserService.saveWechatUser(we);
                if (!PubFun.isNull(we.getNickName())) {
                    wCheck.setNickName(Base64.decodeToString(we.getNickName()));
                    wCheck.setRawData(Base64.decodeToString(we.getRawData()));
                }
            }
            json.put("obj", wCheck);
            json.put("sessionId", session.getId());
        } else {
            json.put("msg", "登录出错，请联系客服！");
        }
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

    @RequestMapping("/queryAnnouncement")
    public void queryAnnouncement(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
//        Long addressId = Long.parseLong(pd.getString("id"));
        pd.put("we_code_id", 1);
        pd.put("codeType", "ANNOUNCEMENT_TYPE");
        List<HashMap<String, Object>> lwis = wechatIndentService.findCType(pd);
        json.put("obj", lwis.get(0));
        super.writeJson(json, response);
    }

    @RequestMapping("/shareInfo")
    public void shareInfo(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        String userId = pd.getString("user_id");
        WechatUserShare wechatUserShare = new WechatUserShare();
        wechatUserShare.setSharingUserId(Long.parseLong(userId));
        wechatUserShare.setCreateDate(new Date());
        wechatUserShare.setCreateTime(new Date());
        wechatUserShare.setModifyDate(new Date());
        wechatUserShare.setModifyTime(new Date());
        wechatUserService.dealUserShareInfo(wechatUserShare);
        super.writeJson(json, response);
    }

    @RequestMapping("/dealShareInfo")
    public void dealShareInfo(HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        String encryptedData = pd.getString("encryptedData");
        String sessionKey = pd.getString("sessionKey");
        String iv = pd.getString("iv");
        String userId = pd.getString("userId");
        JSONObject dealRes = dealEncryptData(encryptedData, sessionKey, iv);
        String openGId = PubFun.isNull(dealRes.getString("openGId")) ? "" : dealRes.getString("openGId");
        String timestamp = dealRes.getJSONObject("watermark").getString("timestamp");
        System.out.println("data:" + dealRes);
        json.put("openGId", openGId);
        if (!"".equals(openGId)) {
            WechatUserShare wechatUserShare = new WechatUserShare();
            wechatUserShare.setAttractedUserId(Long.parseLong(userId));
            wechatUserShare.setOpenGroupId(openGId);
            wechatUserShare.setCreateDate(new Date());
            wechatUserShare.setCreateTime(new Date());
            wechatUserShare.setModifyDate(new Date());
            wechatUserShare.setModifyTime(new Date());
            wechatUserService.dealUserShareInfo(wechatUserShare);
        }
        super.writeJson(json, response);
    }

    public JSONObject dealEncryptData(String encryptedData,String sessionkey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*public static void main(String[] args) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("appid", "wx255aa55368e8f275");
        params.put("secret", "33df13966f5562cbbf1b42cc10b18125");
        params.put("js_code", "021ndiG72gcemS0fBkE72EqCG72ndiGn");
        params.put("grant_type", "authorization_code");
//        post("https://api.weixin.qq.com/sns/jscode2session", params);
    }*/

}


