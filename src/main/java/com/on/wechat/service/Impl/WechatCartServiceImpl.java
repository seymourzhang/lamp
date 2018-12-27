package com.on.wechat.service.Impl;

import com.on.util.common.PageData;
import com.on.wechat.entity.WechatShoppingCart;
import com.on.wechat.repository.WechatCartRepository;
import com.on.wechat.service.WechatCartService;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Service
public class WechatCartServiceImpl implements WechatCartService {
    @Autowired
    private WechatCartRepository wechatCartRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<HashMap<String, Object>> findByUserId(PageData pd) {
//        return wechatCartRepository.findByUserId(userId);
        int userId = pd.getInteger("userId");
        String sql = "select " +
                    "  wsc.id, " +
                    "  truncate(wsc.amount, 0) amount, " +
                    "  wsc.goods_id    goodsId, " +
                    "  wsc.goods_name  goodsName, " +
                    "  truncate(wsc.goods_price, 1) goodsPrice, " +
                    "  wsc.modify_datetime modifyDateTIme, " +
                    "  wsc.operation_date operationDate, " +
                    "  wsc.thumb_url thumbUrl, " +
                    "  truncate(wsc.total, 0) total, " +
                    "  truncate(wsc.total_amount, 1) totalAmount, " +
                    "  wsc.user_id userId, " +
                    "  wc.we_code_name categoryName," +
                    "  wc.we_bak2 categoryValue " +
                    "from wechat_shopping_cart wsc " +
                    "  left join wechat_code wc on wc.we_code_id = wsc.category_type and wc.we_code_type = 'CATEGORY_TYPE' " +
                    "where wsc.user_id = ?1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public List<WechatShoppingCart> findByUserIdAndType(int userId, String cType) {
        return wechatCartRepository.findByUserIdAndType(userId, cType);
    }

    public WechatShoppingCart save(WechatShoppingCart wechatShoppingCart) {
        return wechatCartRepository.save(wechatShoppingCart);
    }

    public WechatShoppingCart findById(Long cartId) {
        return wechatCartRepository.findOne(cartId);
    }

    public void delete(Long cartId) {
        wechatCartRepository.delete(cartId);
    }

    public void deleteByUserId(int userId) {
        wechatCartRepository.deleteAllByUserId(userId);
    }
}
