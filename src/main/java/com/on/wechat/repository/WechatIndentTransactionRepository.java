package com.on.wechat.repository;


import com.on.util.common.PageData;
import com.on.wechat.entity.WechatIndentTransaction;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WechatIndentTransactionRepository extends JpaRepository<WechatIndentTransaction, Long> {



}
