package com.on.util.service;

import com.on.util.Entity.OnACode;

import java.util.List;

public interface OnACodeService {

    List<OnACode> findByCodeType(String codeType);
}
