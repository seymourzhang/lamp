package com.on.wechat.repository;

import com.on.util.common.PageData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WechatCommonRepository {

    @Query(name = "select " +
            "  new map(wit.id indent_id, " +
            "  wit.address_id, " +
            "  wa.name recipientName, " +
            "  wa.gender recipientGender, " +
            "  wa.tel recipientTel, " +
            "  wa.address recipientAddress, " +
            "  wit.money_sum totalAmount, " +
            "  wid.user_id, " +
            "  wid.car_id, " +
            "  wid.good_amount, " +
            "  wid.good_id, " +
            "  wid.good_price, " +
            "  wid.operation_date) " +
            "from wechat_indent_transaction wit " +
            "  left join wechat_indent_detail wid on wid.indent_id = wit.id " +
            "  left join wechat_address wa on wa.id = wit.address_id " +
            "where wit.id = :indentId", nativeQuery = true)
    List<Map<String, Object>> findIndentInfo(@Param("indentId") Long indentId);

}
