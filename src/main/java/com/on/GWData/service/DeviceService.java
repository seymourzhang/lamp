package com.on.GWData.service;


import com.on.GWData.Entity.OnAGWData;
import com.on.util.common.PageData;

/**
 * 控制器服务接口
 * Created by Raymon on 7/7/2016.
 */
public interface DeviceService {
    /**
     * 开始服务
     */
    void start();

    OnAGWData insert(OnAGWData onAGWData) throws Exception ;


}
