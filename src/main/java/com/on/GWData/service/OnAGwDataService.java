package com.on.GWData.service;

import com.on.GWData.Entity.OnADealData;
import com.on.GWData.Entity.OnAGWData;
import com.on.user.entity.OnAUser;

import java.util.List;

public interface OnAGwDataService {

    List<OnADealData> findByOrder();

    OnAGWData findReqDealData();

}
