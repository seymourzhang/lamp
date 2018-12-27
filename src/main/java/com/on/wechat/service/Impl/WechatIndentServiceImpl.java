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
    private WechatIndentTransactionRepository wechatIndentTransactionRepository;

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
        int userId = pd.getInteger("userId");
        String sql = "select " +
                "  indent_id, " +
                "  indent_code, " +
                "  money_sum, " +
                "  operation_type, " +
                "  user_id, " +
                "  operation_date " +
                "from wechat_indent_completion wic " +
                "where wic.user_id = ?1 " +
                "union all " +
                "select " +
                "  id indent_id, " +
                "  indent_code, " +
                "  money_sum, " +
                "  operation_type, " +
                "  user_id, " +
                "  operation_date " +
                "from wechat_indent_transaction wit " +
                "where wit.user_id = ?1 " +
                "order by operation_date desc" ;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public List<PageData> findIndentInfo(@Param("indentId") Long indentId) {
        String sql = "select " +
                "  wit.id indent_id, " +
                "  wit.address_id, " +
                "  wit.money_sum totalAmount, " +
                "  wit.operation_type operationType, " +
                "  wit.indent_code indentCode, " +
                "  wa.name recipientName, " +
                "  wa.gender recipientGender, " +
                "  wa.tel recipientTel, " +
                "  wa.address recipientAddress, " +
                "  wid.user_id, " +
                "  wid.car_id, " +
                "  wid.good_amount, " +
                "  wid.good_id, " +
                "  wid.good_name, " +
                "  wid.good_price, " +
                "  wid.money_sum total, " +
                "  wid.operation_date " +
                "from wechat_indent_transaction wit " +
                "  left join wechat_indent_detail wid on wid.indent_id = wit.id " +
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
        WechatIndentTransaction wit = (WechatIndentTransaction) pd.get("wit");
        String operationType = wit.getOperationType();
        if ("05".equals(operationType) || "06".equals(operationType) || "07".equals(operationType)) {
            wechatIndentTransactionRepository.delete(wit.getId());
        } else {
            wit = wechatIndentTransactionRepository.save(wit);
        }
        Long indentId = wit.getId();

        Date now = new Date();
        WechatIndentChange wic = new WechatIndentChange();
        wic.setIndentId(indentId);
        wic.setAddressId(wit.getAddressId());
        wic.setMoneySum(wit.getMoneySum());
        wic.setUserId(wit.getUserId());
        wic.setOperationType(wit.getOperationType());
        wic.setModifyDatetime(now);
        wic.setOperationDate(now);
        wechatIndentChangeRepository.save(wic);
    }

}
