package com.on.schedule.service.Impl;

import com.on.GWData.Entity.OnADealData;
import com.on.GWData.repository.OnADealDataRepository;
import com.on.schedule.service.DealDataService;
import com.on.schedule.service.OnADealDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class DealDataServiceImpl implements DealDataService, Runnable{
    private static final Logger mLogger = LoggerFactory.getLogger(DealDataServiceImpl.class);

    public void setOnADealDataService(OnADealDataService onADealDataService) {
        this.onADealDataService = onADealDataService;
    }

    private OnADealDataService onADealDataService = null;

    private static int i = 0;

    private final Lock queueLock = new ReentrantLock();

    @Override
    @Transactional
    public void run() {
        try {
            queueLock.lock();
            String _res = onADealDataService.callProcedule();
            mLogger.info("DealResult:" + _res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
            mLogger.info("DealData ending now~~~~~~~~~~~~~~~~~~~!");
        }
        mLogger.info("DealDataServiceStartNum: " + ++i);
    }

    public void start() {
        try {
            new Thread(this).start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
