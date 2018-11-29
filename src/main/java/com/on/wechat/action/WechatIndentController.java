package com.on.wechat.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.on.util.action.BaseAction;
import com.on.util.common.*;
import com.on.wechat.entity.WechatGoods;
import com.on.wechat.entity.WechatIndentDetail;
import com.on.wechat.entity.WechatIndentTransaction;
import com.on.wechat.entity.WechatShoppingCart;
import com.on.wechat.service.WechatIndentService;
import com.on.wechat.wxpay.sdk.MineConfig;
import com.on.wechat.wxpay.sdk.WXPay;
import com.on.wechat.wxpay.sdk.WXPayConstants;
import com.on.wechat.wxpay.sdk.WXPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@SpringBootApplication
@RequestMapping("indent")
public class WechatIndentController extends BaseAction {

    private static Logger mLogger = Logger.getLogger(WechatIndentController.class);

    @Autowired
    private WechatIndentService wechatIndentService;

    private String KEY = "1qaz2wsx3edc4rfvseymourzhanghaha";

    private String appId = "";

    @RequestMapping("/goods")
    public void goods(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
//        int userId = pd.getInteger("user_id");
        JSONObject json = new JSONObject();
        List<WechatGoods> lwsc = wechatIndentService.findGoods(pd);
        if (pd.get("goods_id") != null) {
            json.put("data", lwsc.get(0));
        } else {
            json.put("data", lwsc);
        }
        super.writeJson(json, response);
    }

    @RequestMapping("/generateIndent")
    public void generateIndent(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        String userId = pd.getString("userId");
        String totalAmount = pd.getString("totalAmount");
        String addressId = pd.getString("addressId");
        String indentCode = Tools.getOrderIdByUUId();

        WechatIndentTransaction wit = new WechatIndentTransaction();
        wit.setUserId(userId);
        wit.setMoneySum(new BigDecimal(totalAmount).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP));
        wit.setIndentCode(indentCode);
        wit.setAddressId(addressId);
        wit.setOperationType("01");
        wit.setModifyDatetime(new Date());
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
    public void indents(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
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
            } else if ("06".equals(wit.get("operation_type"))) {
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
        json.put("data", resList);
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
        //接口调用总金额单位为分换算一下(测试金额改成1,单位为分则是0.01,根据自己业务场景判断是转换成float类型还是int类型)
        //String amountFen = Integer.valueOf((Integer.parseInt(amount)*100)).toString();
        //String amountFen = Float.valueOf((Float.parseFloat(amount)*100)).toString();
        String amountFen = "1";
        //设置body变量 (支付成功显示在微信支付 商品详情中)
        String body = "啦啦啦测试";
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
                resultJson.put("returnCode", "SUCCESS");
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
                Map<String, String> paySign = new HashMap<>();
                paySign.put("appId", mineConfig.getAppID());
                paySign.put("nonceStr", nonceStr);
                paySign.put("package", "prepay_id=" + resp.get("prepay_id"));
                paySign.put("signType", WXPayConstants.MD5);
                paySign.put("timeStamp", timeStamp.toString());
                String signPay = WXPayUtil.generateSignature(paySign, mineConfig.getKey());
                resultJson.put("paySign", signPay);
            } else {
                resultJson.put("return_obj", resp);
            }
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resultJson);
        super.writeJson(resultJson, response);
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

        WechatIndentTransaction wit = wechatIndentService.findById(Long.parseLong(indentId));
        wit.setOperationType(indentType);
        if ("Success".equals(resultIndent)) {
            pd.put("wit", wit);
            wechatIndentService.modifyIndentStatus(pd);
            json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        }
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
        mLogger.info(request);
        super.writeJson(json, response);
    }

    /*@RequestMapping("/generateSignature")
    public void generateSignature(HttpServletRequest request, HttpServletResponse response) {
        logger.info("微信 支付接口生成签名 方法开始");
        //实例化返回对象
        JSONObject resultJson = new JSONObject();

        //获得参数(微信统一下单接口生成的prepay_id )
        String prepayId = request.getParameter("prepayId");
        //创建 时间戳
        String timeStamp = Long.valueOf(System.currentTimeMillis()).toString();
        //创建 随机串
        String nonceStr = Tools.getOrderIdByUUId().replaceAll("-", "");
        //创建 MD5
        String signType = "MD5";

        //创建hashmap(用户获得签名)
        SortedMap<String, String> paraMap = new TreeMap<String, String>();
        //设置(小程序ID)(这块一定要是大写)
        paraMap.put("appId", PubFun.getPropertyValue("Pub.APPLYID"));
        //设置(时间戳)
        paraMap.put("timeStamp", timeStamp);
        //设置(随机串)
        paraMap.put("nonceStr", nonceStr);
        //设置(数据包)
        paraMap.put("package", "prepay_id="+prepayId);
        //设置(签名方式)
        paraMap.put("signType", signType);


        //调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        String stringA = Tools.formatUrlMap(paraMap, false, false);
        //第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。(签名)
        String sign = MD5(stringA+"&key=" + KEY).toUpperCase();

        if(sign != null && !"".equals(sign)){
            //返回签名信息
            resultJson.put("sign", sign);
            //返回随机串(这个随机串是新创建的)
            resultJson.put("nonceStr", nonceStr);
            //返回时间戳
            resultJson.put("timeStamp", timeStamp);
            //返回数据包
            resultJson.put("package", "prepay_id="+prepayId);

            logger.info("微信 支付接口生成签名 设置返回值");
        }
        logger.info("微信 支付接口生成签名 方法结束");
        super.writeJson(resultJson, response);
    }*/
}
