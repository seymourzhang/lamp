package com.on.wechat.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.on.util.action.BaseAction;
import com.on.util.common.PageData;
import com.on.util.common.Tools;
import com.on.wechat.entity.WechatGoods;
import com.on.wechat.entity.WechatIndentDetail;
import com.on.wechat.entity.WechatIndentTransaction;
import com.on.wechat.entity.WechatShoppingCart;
import com.on.wechat.service.WechatIndentService;
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

    @Autowired
    private WechatIndentService wechatIndentService;

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
        wit.setMoneySum(new BigDecimal(totalAmount));
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
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
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
}
