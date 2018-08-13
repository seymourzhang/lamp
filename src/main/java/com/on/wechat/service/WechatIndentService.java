package com.on.wechat.service;

import com.on.util.common.PageData;
import com.on.wechat.entity.WechatGoods;
import com.on.wechat.entity.WechatIndentTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WechatIndentService {

    List<WechatGoods> findGoods(PageData pd);

    WechatIndentTransaction dealTransaction(PageData pd);

    List<HashMap<String, Object>> queryTransactions(PageData pd);

    List<PageData> findIndentInfo(Long indentId);
}
