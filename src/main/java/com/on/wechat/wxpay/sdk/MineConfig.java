package com.on.wechat.wxpay.sdk;

import com.on.util.common.PubFun;
import com.on.wechat.wxpay.sdk.IWXPayDomain;
import com.on.wechat.wxpay.sdk.WXPayConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MineConfig extends WXPayConfig {
    private byte[] certData;

    private String key;

    public MineConfig() throws Exception {
//        String certPath = "/path/to/apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
    }

    public String getAppID() {
        return PubFun.getPropertyValue("Pub.APPLYID");
    }

    public String getMchID() {
        return PubFun.getPropertyValue("Pub.MCHID");
    }

    public String getKey() {
//        return "1qaz2wsx3edc4rfvseymourzhanghaha";
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public IWXPayDomain getWXPayDomain() {
        return IWXPayDomainImpl.instance();
    }

}
