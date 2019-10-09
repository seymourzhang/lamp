package com.on.wechat.service;

import com.on.util.common.PageData;
import com.on.wechat.entity.WechatCode;
import com.on.wechat.entity.WechatGoods;
import com.on.wechat.entity.WechatIndentCompletion;
import com.on.wechat.entity.WechatIndentTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WechatIndentService {

    List<Map<String, Object>> findGoods(PageData pd);

    List<HashMap<String, Object>> findCType(PageData pd);

    WechatIndentTransaction dealTransaction(PageData pd);

    List<HashMap<String, Object>> queryTransactions(PageData pd);

    List<PageData> findIndentInfo(Long indentId);

    WechatIndentTransaction findById(Long id);

    void modifyIndentStatus(PageData pd);

    List<HashMap<String, Object>> findDeliverData(PageData pd);

    WechatIndentCompletion findByIndentId(Long indentId);

    void saveGoods(PageData pd);

}
