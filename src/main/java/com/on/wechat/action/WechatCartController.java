package com.on.wechat.action;

import com.alibaba.fastjson.JSONObject;
import com.on.util.action.BaseAction;
import com.on.util.common.PageData;
import com.on.wechat.entity.WechatShoppingCart;
import com.on.wechat.service.WechatCartService;
import com.on.wechat.service.WechatIndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@SpringBootApplication
@RequestMapping("cart")
public class WechatCartController extends BaseAction {

    @Autowired
    private WechatCartService wechatCartService;

    @Autowired
    private WechatIndentService wechatIndentService;

    @RequestMapping("/show")
    public void show(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        int userId = pd.getInteger("user_id");
        JSONObject json = new JSONObject();
        pd.put("userId", userId);
        List<HashMap<String, Object>> lwsc = wechatCartService.findByUserId(pd);
        List<PageData> lpd = new ArrayList<>();
        for (HashMap<String, Object> pageData : lwsc) {
            PageData temp = new PageData();
            temp.put("id", pageData.get("id") == null ? "" : pageData.get("id"));
            temp.put("amount", pageData.get("amount") == null ? "" : pageData.get("amount").toString());
            temp.put("goodsId", pageData.get("goodsId") == null ? "" : pageData.get("goodsId").toString());
            temp.put("goodsName", pageData.get("goodsName") == null ? "" : pageData.get("goodsName").toString());
            temp.put("goodsPrice", pageData.get("goodsPrice") == null ? "" : pageData.get("goodsPrice").toString());
            temp.put("modifyDateTIme", pageData.get("modifyDateTIme") == null ? "" : pageData.get("modifyDateTIme").toString());
            temp.put("operationDate", pageData.get("operationDate") == null ? "" : pageData.get("operationDate").toString());
            temp.put("thumbUrl", pageData.get("thumbUrl") == null ? "" : pageData.get("thumbUrl").toString());
            temp.put("total", pageData.get("total") == null ? "" : pageData.get("total").toString());
            temp.put("totalAmount", pageData.get("totalAmount") == null ? "" : pageData.get("totalAmount").toString());
            temp.put("userId", pageData.get("userId") == null ? "" : pageData.get("userId").toString());
            temp.put("categoryName", pageData.get("categoryName") == null ? "" : pageData.get("categoryName").toString());
            temp.put("categoryValue", pageData.get("categoryValue") == null ? "" : pageData.get("categoryValue").toString());
            lpd.add(temp);
        }
        json.put("data", lpd);
        super.writeJson(json, response);
    }

    @RequestMapping("/shopping")
    public void shopping(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        JSONObject pdParam = (JSONObject) pd.get("goods");
        String thumbUrl = pdParam.getString("thumbUrl");
        int userId = pdParam.getInteger("userId");
        String goodsId = pdParam.getString("goodsId");
        Long amount = Long.parseLong(pdParam.getString("amount"));
//        BigDecimal total = new BigDecimal(pdParam.getString("total"));
        String cType = pdParam.getString("categoryType");
        BigDecimal goodPrice = pdParam.getBigDecimal("goodsPrice");
        BigDecimal totalAmount = pdParam.getBigDecimal("totalAmount");
        List<WechatShoppingCart> lwsc = wechatCartService.findByUserIdAndType(userId, cType);
        for (WechatShoppingCart cart : lwsc) {
            String goodsIdPre = cart.getGoodsId();
            if (goodsId.equals(goodsIdPre)) {
                cart.setModifyDatetime(new Date());
                cart.setAmount(cart.getAmount() + amount);
                cart.setTotalAmount(cart.getTotalAmount().add(totalAmount));
                cart.setCategoryType(cType);
                wechatCartService.save(cart);
                json.put("meta", JSONObject.parseObject("{'code': '0','message': '保存成功'}"));
                super.writeJson(json, response);
                return;
            }
        }
        WechatShoppingCart weCart = new WechatShoppingCart();
        weCart.setGoodsId(goodsId);
        weCart.setGoodsName(pdParam.getString("goodsName"));
        weCart.setGoodsPrice(goodPrice);
        weCart.setModifyDatetime(new Date());
        weCart.setThumbUrl(thumbUrl);
        weCart.setOperationDate(new Date());
        weCart.setUserId(pdParam.getString("userId"));
        weCart.setAmount(amount);
        weCart.setCategoryType(cType);
//        weCart.setTotal(new BigDecimal(pdParam.getString("total")));
        weCart.setTotalAmount(totalAmount);
        wechatCartService.save(weCart);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '保存成功'}"));
        super.writeJson(json, response);
    }

    @RequestMapping("/transaction")
    public void transaction(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        wechatIndentService.dealTransaction(pd);
        super.writeJson(json, response);
    }

    @RequestMapping("/updateAmount")
    public void updateAmount(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        String amount = pd.getString("amount");
        String totalAmount = pd.getString("totalAmount");
        WechatShoppingCart temp = wechatCartService.findById(Long.parseLong(pd.getString("cartId")));
        temp.setAmount(Long.parseLong(amount));
        temp.setTotalAmount(new BigDecimal(totalAmount));
        temp.setModifyDatetime(new Date());
        temp = wechatCartService.save(temp);
        PageData wsc = new PageData();
        wsc.put("id", temp.getId());
        wsc.put("userId", temp.getUserId());
        wsc.put("goodsId", temp.getGoodsId());
        wsc.put("goodsPrice", temp.getGoodsPrice());
        wsc.put("goodsName", temp.getGoodsName());
        wsc.put("thumbUrl", temp.getThumbUrl());
        wsc.put("amount", temp.getAmount());
        wsc.put("total", temp.getTotal());
        wsc.put("totalAmount", temp.getTotalAmount().divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP));
        json.put("data", wsc);
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '更新成功'}"));
        super.writeJson(json, response);
    }

    @RequestMapping("/cartDelete")
    public void cartDelete(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject json = new JSONObject();
        String option = pd.getString("option");
        if ("delete".equals(option)) {
            Long cartId = Long.parseLong(pd.getString("cartId"));
            wechatCartService.delete(cartId);
        } else if ("clear".equals(option)){
            int userId = pd.getInteger("userId");
            wechatCartService.deleteByUserId(userId);
        }
        json.put("meta", JSONObject.parseObject("{'code': '0','message': '删除成功'}"));
        super.writeJson(json, response);
    }
}
