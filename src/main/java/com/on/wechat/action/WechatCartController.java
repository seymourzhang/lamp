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
        List<WechatShoppingCart> lwsc = wechatCartService.findByUserId(userId);
        json.put("data", lwsc);
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
        BigDecimal goodPrice = pdParam.getBigDecimal("goodsPrice");
        List<WechatShoppingCart> lwsc = wechatCartService.findByUserId(userId);
        for (WechatShoppingCart cart : lwsc) {
            String goodsIdPre = cart.getGoodsId();
            if (goodsId.equals(goodsIdPre)) {
                cart.setModifyDatetime(new Date());
                cart.setAmount(cart.getAmount() + amount);
                cart.setTotalAmount(goodPrice.multiply(new BigDecimal(pdParam.getString("amount"))).add(cart.getTotalAmount()));
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
//        weCart.setTotal(new BigDecimal(pdParam.getString("total")));
        weCart.setTotalAmount(goodPrice.multiply(new BigDecimal(amount)));
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
        WechatShoppingCart temp = wechatCartService.findById(Long.parseLong(pd.getString("cartId")));
        temp.setAmount(Long.parseLong(pd.getString("amount")));
        temp.setTotalAmount(new BigDecimal(pd.getString("amount")).multiply(temp.getGoodsPrice()));
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
