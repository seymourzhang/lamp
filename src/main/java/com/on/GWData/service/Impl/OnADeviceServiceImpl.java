package com.on.GWData.service.Impl;

import com.on.GWData.Entity.OnADevice;
import com.on.GWData.repository.OnADeviceRepository;
import com.on.GWData.service.OnADeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnADeviceServiceImpl implements OnADeviceService {

    @Autowired
    private OnADeviceRepository onADeviceRepository;

    public List<OnADevice> queryImeis() {
        return onADeviceRepository.findAll();
    }
}
