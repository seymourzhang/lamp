package com.on.util.service.Impl;

import com.on.util.Entity.OnACode;
import com.on.util.repository.OnACodeRepository;
import com.on.util.service.OnACodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnACodeServiceImpl implements OnACodeService {

    @Autowired
    private OnACodeRepository onACodeRepository;

    public List<OnACode> findByCodeType(String codeType) {
        return onACodeRepository.findAllByCodeType(codeType);
    }
}
