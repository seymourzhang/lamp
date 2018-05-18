package com.on.schedule.service.Impl;

import com.on.GWData.repository.OnADealDataRepository;
import com.on.schedule.service.OnADealDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnADealDataServiceImpl implements OnADealDataService{

    @Autowired
    private OnADealDataRepository onADealDataRepository;

    public String callProcedule() {
        String _res = onADealDataRepository.on_a_TransGWData();
        return _res;
    }
}
