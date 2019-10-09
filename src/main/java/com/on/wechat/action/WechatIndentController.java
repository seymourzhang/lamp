package com.on.wechat.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.on.util.action.BaseAction;
import com.on.util.common.*;
import com.on.wechat.entity.WechatCode;
import com.on.wechat.entity.WechatIndentCompletion;
import com.on.wechat.entity.WechatIndentTransaction;
import com.on.wechat.service.WechatIndentService;
import com.on.wechat.wxpay.sdk.MineConfig;
import com.on.wechat.wxpay.sdk.WXPay;
import com.on.wechat.wxpay.sdk.WXPayConstants;
import com.on.wechat.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@RestController
@SpringBootApplication
@RequestMapping("indent")
public class WechatIndentController extends BaseAction {

    private static Logger mLogger = LoggerFactory.getLogger(WechatIndentController.class);

    @Autowired
    private WechatIndentService wechatIndentService;

    private String KEY = "1qaz2wsx3edc4rfvseymourzhanghaha";

    private String appId = "";

    @RequestMapping("/goods")
    public void goods(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
//        int userId = pd.getInteger("user_id");
        JSONObject json = new JSONObject();
        List<Map<String, Object>> lwsc = wechatIndentService.findGoods(pd);
        if (pd.get("goods_id") != null) {
            json.put("data", lwsc.get(0));
        } else {
            json.put("data", lwsc);
        }
        pd.put("codeType", "CATEGORY_TYPE");
        json.put("cTypes", wechatIndentService.findCType(pd));
        super.writeJson(json, response);
    }

    @RequestMapping("/addMaterials")
    public void addMaterials(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
//        int userId = pd.getInteger("user_id");
        JSONObject json = new JSONObject();
        wechatIndentService.saveGoods(pd);
        super.writeJson(json, response);
    }

    @RequestMapping("/generateIndent")
    public void generateIndent(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        String userId = pd.getString("userId");
        String totalAmount = pd.getString("totalAmount");
        String addressId = pd.getString("addressId");
        String formId = pd.getString("formId");
        String indentCode = Tools.getOrderIdByUUId();
        Date now = new Date();

        WechatIndentTransaction wit = new WechatIndentTransaction();
        wit.setUserId(userId);
        wit.setMoneySum(new BigDecimal(totalAmount).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP));
        wit.setIndentCode(indentCode);
        wit.setAddressId(addressId);
        wit.setFormId(formId);
        wit.setOperationType("01");
        wit.setModifyDatetime(now);
        wit.setCreateDatetime(now);
        wit.setOperationDate(new Date());
        pd.put("wit", wit);

        wit = wechatIndentService.dealTransaction(pd);
        JSONObject json = new JSONObject();
        json.put("data", wit);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        super.writeJson(json, response);
    }

    @RequestMapping("/indentDetail")
    public void indentDetail(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        List<PageData> wits = wechatIndentService.findIndentInfo(Long.parseLong(pd.getString("id")));
        json.put("data", wits);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': ''}"));
        super.writeJson(json, response);
    }

    @RequestMapping("/indentQueryAll")
    public void indentQueryAll(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        String curTab = pd.getString("curTab");
        List<HashMap<String, Object>> wits = wechatIndentService.queryTransactions(pd);
        List<HashMap<String, Object>> transType01 = new ArrayList<>();
        List<HashMap<String, Object>> transType02 = new ArrayList<>();
        List<HashMap<String, Object>> transType03 = new ArrayList<>();
        List<HashMap<String, Object>> transType04 = new ArrayList<>();
        List<HashMap<String, Object>> transType05 = new ArrayList<>();
        List<HashMap<String, Object>> transType06 = new ArrayList<>();
        List<HashMap<String, Object>> transType07 = new ArrayList<>();
        for (HashMap<String, Object> wit : wits) {
            if ("01".equals(wit.get("operation_type"))) {
                transType01.add(wit);
            } else if ("02".equals(wit.get("operation_type"))) {
                transType02.add(wit);
            } else if ("03".equals(wit.get("operation_type"))) {
                transType03.add(wit);
            } else if ("04".equals(wit.get("operation_type"))) {
                transType04.add(wit);
            } else if ("05".equals(wit.get("operation_type"))) {
                transType05.add(wit);
            } else if ("06".equals(wit.get("operation_type")) || "08".equals(wit.get("operation_type"))) {
                transType06.add(wit);
            } else if ("07".equals(wit.get("operation_type"))) {
                transType07.add(wit);
            }
        }
        List<PageData> resList = new ArrayList<>();

        PageData res1 = new PageData();
        PageData res2 = new PageData();
        PageData res3 = new PageData();
        PageData res4 = new PageData();
        PageData res5 = new PageData();
        PageData res6 = new PageData();
        PageData res7 = new PageData();
        res1.put("length", transType01.size());
        res1.put("maintainRecordlist", transType01);
        res2.put("length", transType02.size());
        res2.put("maintainRecordlist", transType02);
        res3.put("length", transType03.size());
        res3.put("maintainRecordlist", transType03);
        res4.put("length", transType04.size());
        res4.put("maintainRecordlist", transType04);
        res5.put("length", transType05.size());
        res5.put("maintainRecordlist", transType05);
        res6.put("length", transType06.size());
        res6.put("maintainRecordlist", transType06);
        res7.put("length", transType07.size());
        res7.put("maintainRecordlist", transType07);
        resList.add(res1);
        resList.add(res2);
        resList.add(res3);
        resList.add(res4);
        resList.add(res5);
        resList.add(res6);
        resList.add(res7);
        if ("0".equals(curTab)) {
            json.put("data", res1);
        } else if ("1".equals(curTab)) {
            json.put("data", res2);
        } else if ("2".equals(curTab)) {
            json.put("data", res3);
        } else if ("3".equals(curTab)) {
            json.put("data", res4);
        } else if ("4".equals(curTab)) {
            json.put("data", res5);
        } else if ("5".equals(curTab)) {
            json.put("data", res6);
        } else if ("6".equals(curTab)) {
            json.put("data", res7);
        }
        int[] lengths = {transType01.size(), transType02.size(), transType03.size(), transType04.size(),
                         transType05.size(), transType06.size(), transType07.size()};
        json.put("lengths", lengths);
//        json.put("data", resList);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        super.writeJson(json, response);
    }


    @RequestMapping("/createUnifiedOrder")
    public void createUnifiedOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
        JSONObject resultJson = new JSONObject();
//        PageData pd = this.getPageData();
        //接受参数(金额)
        String amount = request.getParameter("amount");
        //接受参数(openid)
        String openid = request.getParameter("openid");

        String indentCode = request.getParameter("indent_code");

        String indentId = request.getParameter("indentId");
        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        //String amountFen = Integer.valueOf((Integer.parseInt(amount)*100)).toString();
        //String amountFen = Float.valueOf((Float.parseFloat(amount)*100)).toString();
        String amountFen = "1";
        //设置body变量 (支付成功显示在微信支付 商品详情中)
        String body = "烟台樱桃";
        //设置随机字符串
        String nonceStr = WXPayUtil.generateNonceStr();
        //设置商户订单号
        String outTradeNo = indentCode;


        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        //设置请求参数(商品描述)
        paraMap.put("body", body);
        //设置终端设备号
        paraMap.put("device_info", "");
        //设置请求参数(商户订单号)
        paraMap.put("out_trade_no", outTradeNo);
        //货币类型
        paraMap.put("fee_type", "CNY");
        //交易金额
        paraMap.put("total_fee", new BigDecimal(amount).multiply(new BigDecimal(100)).divide(new BigDecimal(1), 0, BigDecimal.ROUND_UP).toString());
        //终端IP
        paraMap.put("spbill_create_ip", "127.0.0.0");
        //通知地址
//        paraMap.put("notify_url", "https://vjdot.com/indent/notifyResult");
        paraMap.put("notify_url", "https://vjdot.com/indent/notifyResult");
        //交易类型
        paraMap.put("trade_type", "JSAPI");
        //用户标识
        paraMap.put("openid", openid);

        MineConfig mineConfig = new MineConfig();
        mineConfig.setKey("1qaz2wsx3edc4rfvseymourzhanghaha");
        WXPay wxPay = new WXPay(mineConfig);
        try {
            Map<String, String> resp = wxPay.unifiedOrder(paraMap);
            Long timeStamp = System.currentTimeMillis() / 1000;
            if ("SUCCESS".equals(resp.get("result_code"))) {
                resultJson.put("prepayId", resp.get("prepay_id"));
                resultJson.put("return_code", resp.get("return_code"));
                resultJson.put("result_code", resp.get("result_code"));
                resultJson.put("mchId", resp.get("mch_id"));
                resultJson.put("returnMsg", resp.get("return_msg"));
                resultJson.put("tradeType", resp.get("trade_type"));
                resultJson.put("appid", resp.get("appid"));
                resultJson.put("nonceStr", nonceStr);
                resultJson.put("timeStamp", timeStamp.toString());
                resultJson.put("outTradeNo", outTradeNo);
                resultJson.put("package", "prepay_id=" + resp.get("prepay_id"));
                resultJson.put("key", mineConfig.getKey());
                resultJson.put("signType", WXPayConstants.MD5);
                resultJson.put("sign", resp.get("sign"));
                resultJson.put("total_fee", amount);
                Map<String, String> paySign = new HashMap<>();
                paySign.put("appId", mineConfig.getAppID());
                paySign.put("nonceStr", nonceStr);
                paySign.put("package", "prepay_id=" + resp.get("prepay_id"));
                paySign.put("signType", WXPayConstants.MD5);
                paySign.put("timeStamp", timeStamp.toString());
                String signPay = WXPayUtil.generateSignature(paySign, mineConfig.getKey());
                resultJson.put("paySign", signPay);
                resultJson.put("indentId", indentId);
            } else {
                resultJson.putAll(resp);
            }
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resultJson);
        super.writeJson(resultJson, response);
    }

    @RequestMapping("/deliverTemplateMsg")
    public void deliverTemplateMsg(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            PageData pd = this.getPageData();
            String prePayId = pd.getString("prepayId");
            String openId = pd.getString("openId");
            String indentId = pd.getString("indentId");
            String type = pd.getString("type");//after_pay  after_sent
            String templateId = "";
            HashMap<String, Object> map = new HashMap<>();
            map.put("grant_type", "client_credential");
            map.put("appid", PubFun.getPropertyValue("Pub.APPLYID"));
            map.put("secret", PubFun.getPropertyValue("Pub.screte"));
            JSONObject token = PubFun.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + PubFun.getPropertyValue("Pub.APPLYID") + "&secret=" + PubFun.getPropertyValue("Pub.screte"));
            String to = token.getString("access_token");
            if (!"".equals(to)) {
                pd.put("indentId", indentId);
                JSONObject data = new JSONObject();
                JSONObject value1 = new JSONObject();
                JSONObject value2 = new JSONObject();
                JSONObject value3 = new JSONObject();
                JSONObject value4 = new JSONObject();
                JSONObject value5 = new JSONObject();
                JSONObject value6 = new JSONObject();
                JSONObject value7 = new JSONObject();
                List<HashMap<String, Object>> pdResult = wechatIndentService.findDeliverData(pd);
                if ("after_pay".equals(type)) {
                    templateId = PubFun.getPropertyValue("Pub.PAYDELIVER");
                    value1.put("value", pdResult.get(0).get("indent_code").toString());
                    value2.put("value", "高级果：" + pdResult.get(0).get("indent_name").toString() + "【烟台大樱桃】");
                    value3.put("value", pdResult.get(0).get("money_sum").toString());
                    value4.put("value", pdResult.get(0).get("gen_date").toString());
                    value5.put("value", pdResult.get(0).get("pay_date").toString());
                    value6.put("value", pdResult.get(0).get("address").toString());
                } else if ("after_sent".equals(type)) {
                    templateId = PubFun.getPropertyValue("Pub.SENTDELIVER");
                    value1.put("value", pdResult.get(0).get("transport_indent").toString());
                    value2.put("value", "【顺丰速递】");
                    value3.put("value", pdResult.get(0).get("sent_date").toString());
                    value4.put("value", "高级果：" + pdResult.get(0).get("indent_name").toString() + "【烟台大樱桃】");
                    value5.put("value", pdResult.get(0).get("indent_code").toString());
                    value6.put("value", pdResult.get(0).get("address").toString());
                }
                value7.put("value", "烟台生鲜季");
                data.put("keyword1", value1);
                data.put("keyword2", value2);
                data.put("keyword3", value3);
                data.put("keyword4", value4);
                data.put("keyword5", value5);
                data.put("keyword6", value6);
                data.put("keyword7", value7);
                String dataStr = data.toString();
                JSONObject json = new JSONObject();
                json.put("touser", pdResult.get(0).get("open_id").toString());
                json.put("template_id", templateId);
                json.put("page", "/page/indent/indent?id=" + indentId);
                if ("after_pay".equals(type)) {
                    json.put("form_id", prePayId);
                } else if ("after_sent".equals(type)) {
                    json.put("form_id", pdResult.get(0).get("form_id").toString());
                }
                json.put("data", data);
                json.put("emphasis_keyword", "keyword7.DATA");
                System.out.println("json data:" + json);
                result = PubFun.post("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + to, json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        super.writeJson(result, response);
    }

    @RequestMapping("/closeOrder")
    public void closeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject resultJson = new JSONObject();
        PageData pd = this.getPageData();
        String outTradeNo = pd.getString("out_trade_no");
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("out_trade_no", outTradeNo);

        MineConfig mineConfig = new MineConfig();
        mineConfig.setKey("1qaz2wsx3edc4rfvseymourzhanghaha");
        WXPay wxPay = new WXPay(mineConfig);
        try {
            Map<String, String> resp = wxPay.closeOrder(paraMap);
            if ("SUCCESS".equals(resp.get("result_code").toString())) {

            } else {
                mLogger.info("删除失败！");
            }
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resultJson);
        super.writeJson(resultJson, response);
    }

    @RequestMapping("/modifyIndent")
    public void modifyIndent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        String indentId = pd.getString("indent_id");
        String indentType = pd.getString("indent_type");
        String resultIndent = pd.getString("indent_result");
        String transportIndent = pd.getString("transport_indent");
        String recallMsg = pd.getString("recall_msg");

        pd.put("recallMsg", recallMsg);
        pd.put("indentId", indentId);
        pd.put("operationType", indentType);
        pd.put("transportIndent", transportIndent);
        wechatIndentService.modifyIndentStatus(pd);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
    }

    @RequestMapping("/notifyResult")
    public void notifyResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        mLogger.info("notifyResult:");
        mLogger.info("+++++++");
        mLogger.info("+++++++");
        mLogger.info("+++++++");
        mLogger.info("+++++++");
        mLogger.info("+++++++");
        mLogger.info(getDataFromRequest(request));
        super.writeJson(json, response);
    }

    @RequestMapping("/videoCollect")
    public void videoCollect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        pd.put("codeType", "VIDEO_URL");
        pd.put("we_code_id", "1");
        List<HashMap<String, Object>> wc = wechatIndentService.findCType(pd);
        if (wc.size() != 0) {
            String videoUrl = "http://vv.video.qq.com/getinfo?vids=" + wc.get(0).get("weBak3") + "&platform=101001&charge=0&otype=json";//e0301ajih22
            JSONObject token = PubFun.get(videoUrl);
            super.writeJson(token, response);
        }
    }

}
