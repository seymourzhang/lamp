package com.on.schedule.Impl;

import com.on.GWData.repository.OnAGWDataRepository;
import com.on.schedule.DealDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealDataServiceImpl implements DealDataService, Runnable{
    private static final Logger mLogger = LoggerFactory.getLogger(DealDataServiceImpl.class);

    @Autowired
    private OnAGWDataRepository onAGWDataRepository;

    private int i = 0;

    @Override
    public void run() {
        try {
            String res = onAGWDataRepository.on_a_TransGWData();
            mLogger.info("DealResult:" + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLogger.info("DealDataServiceStartNum: " + ++this.i);
    }

    public void start() {
        try {
            new Thread(this).start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
