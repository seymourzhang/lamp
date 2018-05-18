package com.on.schedule.service.Impl;

import com.on.GWData.repository.OnADealDataRepository;
import com.on.GWData.repository.OnAGWDataRepository;
import com.on.GWData.service.Impl.DeviceServiceImpl;
import com.on.GWData.service.Impl.OnAGwDataServiceImpl;
import com.on.schedule.service.DealDataService;
import com.on.schedule.service.JobService;
import com.on.user.repository.OnAUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class JobServiceImpl extends DeviceServiceImpl implements JobService, Runnable {

    private static final Logger mLogger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    OnAUserRepository onAUserRepository;

    @Autowired
    private OnAGWDataRepository onAGWDataRepository;

    private int socketServerPort = 8088;
    //test port at 2018/05/12
//    private int socketServerPort = 8111;
    private boolean state = true;

    /**
     * Socket服务器
     */
    private static ServerSocket socketServer = null;

    @Autowired
    private OnADealDataRepository onADealDataRepository;

    @Autowired
    private DealDataService dealDataService;

    @Override
    public void run() {
        long socketThreadId = 0;
        try {
            if (socketServer == null || socketServer.isClosed()) {
                socketServer = new ServerSocket(socketServerPort);
            }
            if (socketServer == null) {
                mLogger.info("启动Socket服务失败。");
                return;
            }
            mLogger.info("启动Socket服务成功。端口号：" + socketServerPort);
            state = false;
            while (true) {
                try {
                    Socket tempSocket = socketServer.accept();
                    tempSocket.setSoTimeout(10 * 1000);
                    OnAGwDataServiceImpl onGwDataServiceImpl = new OnAGwDataServiceImpl();
                    onGwDataServiceImpl.setSocket(tempSocket);
                    onGwDataServiceImpl.setDeviceService(this);
                    onGwDataServiceImpl.setOnAGWDataRepository(onAGWDataRepository);
                    Thread tThread = new Thread(onGwDataServiceImpl);
                    tThread.start();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (socketServer != null) {
                try {
                    socketServer.close();
                    state = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void start() {
        try {
            if (state) {
                new Thread(this).start();
            } else {
                mLogger.info("lamp 服务正在运行中...");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
