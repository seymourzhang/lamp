package com.on.wechat.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.on.util.common.Page;
import com.on.util.common.PageData;
import com.on.wechat.entity.*;
import com.on.wechat.repository.*;
import com.on.wechat.service.WechatIndentService;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

@Service
public class WechatIndentServiceImpl implements WechatIndentService {

    @Autowired
    private WechatGoodsRepository wechatGoodsRepository;

    @Autowired
    private WechatGoodsSubRepository wechatGoodsSubRepository;

    @Autowired
    private WechatGoodsSizeRepository wechatGoodsSizeRepository;

    @Autowired
    private WechatIndentTransactionRepository wechatIndentTransactionRepository;

    @Autowired
    private WechatIndentCompletionRepository wechatIndentCompletionRepository;

    @Autowired
    private WechatIndentDetailRepository wechatIndentDetailRepository;

    @Autowired
    private WechatIndentChangeRepository wechatIndentChangeRepository;

    @Autowired
    private WechatCartRepository wechatCartRepository;

    @Autowired
    private WechatCodeRepository wechatCodeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String, Object>> findGoods(PageData pd) {
        List<Map<String, Object>> lwg = new ArrayList<>();
        String sql = "select" +
                    "  wg.id, " +
                    "  wg.goods_name goodsName, " +
                    "  wg.goods_price goodsPrice, " +
                    "  wg.goods_status goodsStaus, " +
                    "  wg.goods_type goodsType, " +
                    "  wg.modify_datetime modifyDatetime, " +
                    "  wg.thumb_url thumbUrl, " +
                    "  wc.we_bak2 total," +
                    "  wg.inventory_amount inventoryAmount " +
                    "from wechat_goods wg " +
                    "  left join wechat_code wc on wc.id = wg.goods_type and we_code_type = 'CATEGORY_TYPE' " +
                    "where 1 = 1";
        if (pd.get("goods_id") != null) {
            sql += " and wg.id = ?1";
        }
        Query query = entityManager.createNativeQuery(sql);
        if (pd.get("goods_id") != null) {
            query.setParameter(1, pd.get("goods_id"));
        }
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public void saveGoods(PageData pd) {
        WechatGoods wg = new WechatGoods();
        wg.setGoodsName(pd.get("goodsName").toString());
        wg.setGoodsPrice(new BigDecimal(pd.get("goodsPrice").toString()));
        wg.setGoodsStatus("1");
        wg.setGoodsType("1");
        wg.setInventoryAmount(0);
        wg.setModifyDatetime(new Date());
        wg.setThumbUrl(pd.get("thumbUrl").toString());
        WechatGoods newOne = wechatGoodsRepository.save(wg);
        Long goodsId = newOne.getId();

        List details = Arrays.asList(pd.get("details"));
        List<WechatGoodsSub> lwg = new ArrayList<>();
        for (Object detail : details) {
            WechatGoodsSub wgs = new WechatGoodsSub();
            wgs.setGoodsId(goodsId);
            wgs.setThumbUrlSub(detail.toString());
            wgs.setModifyDatetime(new Date());
            lwg.add(wgs);
        }
        wechatGoodsSubRepository.save(lwg);

        List sizes = Arrays.asList(pd.get("sizes"));
        List<WechatGoodsSize> lwg2 = new ArrayList<>();
        for (Object size : sizes) {
            WechatGoodsSize wgs = new WechatGoodsSize();
            wgs.setGoodsId(goodsId);
            wgs.setGoodsSize(size.toString());
            wgs.setModifyDatetime(new Date());
            lwg2.add(wgs);
        }
        wechatGoodsSizeRepository.save(lwg2);
    }

    public List<HashMap<String, Object>> findCType(PageData pd) {
        List<Map<String, Object>> lwg = new ArrayList<>();
        String sql = "select " +
                     "  create_datetime createDatetime, " +
                     "  modify_datetime modifyDatetime, " +
                     "  we_bak1 weBak1, " +
                     "  we_code_id weCodeId, " +
                     "  we_code_name weCodeName, " +
                     "  we_code_type weCodeType, " +
                     "  we_bak2 weBak2, " +
                     "  we_bak3 weBak3, " +
                     "  we_bak4 weBak4 " +
                     "from wechat_code " +
                     "where we_code_type = ?1";
        if (pd.get("we_code_id") != null) {
            sql += " and we_code_id = ?2";
        }
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, pd.get("codeType"));
        if (pd.get("we_code_id") != null) {
            query.setParameter(2, pd.get("we_code_id"));
        }
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public WechatIndentTransaction dealTransaction(PageData pd) {
        Date now = new Date();
        WechatIndentTransaction wit = (WechatIndentTransaction) pd.get("wit");

        wit = wechatIndentTransactionRepository.save(wit);
        Long indentId = wit.getId();
        JSONArray cartItems = (JSONArray) pd.get("items");
        List<WechatIndentDetail> lwit = new ArrayList<>();
        for (Object cartItem : cartItems) {
            JSONObject cart = (JSONObject)cartItem;
            WechatIndentDetail wid = new WechatIndentDetail();
            wid.setIndentId(indentId.toString());
            wid.setCarId(cart.getString("cartId"));
            wid.setGoodId(cart.getString("goodId"));
            wid.setGoodPrice(cart.getString("goodPrice"));
            wid.setGoodAmount(cart.getString("amount"));
            wid.setMoneySum(cart.getBigDecimal("total"));
            wid.setGoodName(cart.getString("goodName"));
            wid.setUserId(wit.getUserId());
            wid.setModifyDatetime(now);
            wid.setOperationDate(now);
            lwit.add(wid);
            wechatCartRepository.delete(cart.getLong("cartId"));
        }
        lwit = wechatIndentDetailRepository.save(lwit);

        WechatIndentChange wic = new WechatIndentChange();
        wic.setIndentId(indentId);
        wic.setAddressId(wit.getAddressId());
        wic.setMoneySum(wit.getMoneySum());
        wic.setUserId(wit.getUserId());
        wic.setOperationType(wit.getOperationType());
        wic.setModifyDatetime(now);
        wic.setOperationDate(now);
        wechatIndentChangeRepository.save(wic);

        return wit;
    }

    public List<HashMap<String, Object>> queryTransactions(PageData pd) {
        String userId = pd.getString("userId");
        String sql = "select " +
                "  wic.indent_id, " +
                "  wic.indent_code, " +
                "  wic.money_sum, " +
                "  wic.operation_type, " +
                "  wic.user_id, " +
                "  wic.operation_date, " +
                "  ifnull(wic.transport_indent, '') transport_indent, " +
                "  wa.tel, " +
                "  wa.address, " +
                "  wa.gender, " +
                "  wa.name " +
                "from wechat_indent_completion wic " +
                "  left join wechat_address wa on wa.id = wic.address_id ";
                if (!"".equals(userId)) {
                    sql += "where wic.user_id = ?1 ";
                }
                sql += "union all " +
                "select " +
                "  wit.id indent_id, " +
                "  wit.indent_code, " +
                "  wit.money_sum, " +
                "  wit.operation_type, " +
                "  wit.user_id, " +
                "  wit.operation_date, " +
                "  ifnull(wit.transport_indent, '') transport_indent, " +
                "  wa.tel, " +
                "  wa.address, " +
                "  wa.gender, " +
                "  wa.name " +
                "from wechat_indent_transaction wit " +
                "  left join wechat_address wa on wa.id = wit.address_id ";
                if (!"".equals(userId)) {
                    sql += "where wit.user_id = ?1 ";
                }
                sql += "order by operation_date desc" ;
        Query query = entityManager.createNativeQuery(sql);
        if (!"".equals(userId)) {
            query.setParameter(1, userId);
        }
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public List<PageData> findIndentInfo(@Param("indentId") Long indentId) {
        String sql = "select " +
                "  wit.id             indent_id, " +
                "  wit.address_id, " +
                "  wit.money_sum      totalAmount, " +
                "  wit.operation_type operationType, " +
                "  wit.indent_code    indentCode, " +
                "  wit.transport_indent transportIntent, " +
                "  wit.recall_message   recallMessage, " +
                "  wa.name            recipientName, " +
                "  wa.gender          recipientGender, " +
                "  wa.tel             recipientTel, " +
                "  wa.address         recipientAddress, " +
                "  wid.user_id, " +
                "  wid.car_id, " +
                "  wid.good_amount, " +
                "  wid.good_id, " +
                "  wid.good_name, " +
                "  wid.good_price, " +
                "  wid.money_sum      total, " +
                "  wid.operation_date " +
                "from (select " +
                "        id, " +
                "        address_id, " +
                "        money_sum, " +
                "        operation_type, " +
                "        indent_code, " +
                "        transport_indent, " +
                "        '' recall_message " +
                "      from wechat_indent_transaction " +
                "      union all " +
                "      select " +
                "        indent_id id, " +
                "        address_id, " +
                "        money_sum, " +
                "        operation_type, " +
                "        indent_code, " +
                "        transport_indent, " +
                "        recall_message " +
                "      from wechat_indent_completion) wit left join wechat_indent_detail wid on wid.indent_id = wit.id " +
                "  left join wechat_address wa on wa.id = wit.address_id " +
                "where wit.id = ?1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, indentId);
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public WechatIndentTransaction findById(@Param("indentId") Long id) {
        return wechatIndentTransactionRepository.findOne(id);
    }

    public void modifyIndentStatus(PageData pd) {
        Long indentId = Long.parseLong(pd.getString("indentId"));
        WechatIndentTransaction wit = wechatIndentTransactionRepository.findOne(indentId);
        String operationType = pd.getString("operationType");
        String recallMsg = pd.getString("recallMsg");
        String transportIndent = pd.getString("transportIndent");
        Date now = new Date();
        WechatIndentCompletion wic = wechatIndentCompletionRepository.findByIndentId(indentId);
        if ("05".equals(operationType) || "06".equals(operationType) || "07".equals(operationType) || "08".equals(operationType)) {
            if (wic == null) {
                wechatIndentTransactionRepository.delete(indentId);
                wic = new WechatIndentCompletion();
                wic.setAddressId(wit.getAddressId());
                wic.setIndentId(wit.getId().toString());
                wic.setIndentCode(wit.getIndentCode());
                wic.setModifyDatetime(now);
                wic.setMoneySum(wit.getMoneySum());
                wic.setOperationType(operationType);
                wic.setUserId(wit.getUserId());
                if (!"".equals(recallMsg)) {
                    wic.setRecallMessage(recallMsg);
                }
                wic.setTransportIndent(wit.getTransportIndent());
                wic.setOperationDate(now);
            } else {
                wic.setOperationType(operationType);
                if (!"".equals(recallMsg)) {
                    wic.setRecallMessage(recallMsg);
                }
                wic.setModifyDatetime(now);
                wic.setOperationDate(now);
            }
            wechatIndentCompletionRepository.save(wic);
        } else {
            if (wit != null) {
                wit.setOperationType(operationType);
                wit.setTransportIndent("".equals(transportIndent) ? wit.getTransportIndent() : transportIndent);
                wit.setModifyDatetime(now);
            }
            wit = wechatIndentTransactionRepository.save(wit);
        }

        WechatIndentChange wic2 = new WechatIndentChange();
        wic2.setIndentId(indentId);
        wic2.setAddressId(wit == null ? wic.getAddressId() : wit.getAddressId());
        wic2.setTransportIndent(wit == null ? wic.getTransportIndent() : wit.getTransportIndent());
        wic2.setMoneySum(wit == null ? wic.getMoneySum() : wit.getMoneySum());
        wic2.setUserId(wit == null ? wic.getUserId() : wit.getUserId());
        wic2.setOperationType(operationType);
        wic2.setModifyDatetime(now);
        wic2.setOperationDate(now);
        wechatIndentChangeRepository.save(wic2);
    }

    public List<HashMap<String, Object>> findDeliverData(PageData pd) {
        String indentId = pd.getString("indentId");
        String sql = "select " +
                "  wit.indent_code, " +
                "  group_concat(wid.good_name) indent_name, " +
                "  wit.money_sum, " +
                "  wic_gen.operation_date      gen_date, " +
                "  wic_pay.operation_date      pay_date, " +
                "  wa.address," +
                "  wa.tel, " +
                "  concat(wa.name, ' ', if(wa.gender = 'male', '男士', '女士')) name, " +
                "  wic_sent.operation_date                                  sent_date, " +
                "  wic_sent.transport_indent, " +
                "  wu.open_id," +
                "  wit.form_id " +
                "from wechat_indent_transaction wit " +
                "  left join wechat_indent_detail wid on wit.id = wid.indent_id " +
                "  left join wechat_indent_change wic_gen on wic_gen.indent_id = wit.id and wic_gen.operation_type = '01' " +
                "  left join wechat_indent_change wic_pay on wic_pay.indent_id = wit.id and wic_pay.operation_type = '02' " +
                "  left join wechat_indent_change wic_sent on wic_sent.indent_id = wit.id and wic_sent.operation_type = '03' " +
                "  left join wechat_address wa on wa.id = wit.address_id " +
                "  left join wechat_user wu on wu.id = wit.user_id " +
                "where wit.id = ?1 " +
                "group by wit.id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, indentId);
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public WechatIndentCompletion findByIndentId(Long orderId) {
        return wechatIndentCompletionRepository.findByIndentId(orderId);
    }
}
