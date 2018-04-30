package com.on.schedule.Impl;

import com.on.GWData.repository.OnAGWDataRepository;
import com.on.GWData.service.Impl.OnAGwDataServiceImpl;
import com.on.schedule.JobService;
import com.on.user.repository.OnAUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class JobServiceImpl implements JobService, Runnable {

    private static final Logger mLogger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    OnAUserRepository onAUserRepository;

    @Autowired
    OnAGWDataRepository onAGWDataRepository;

    private int socketServerPort = 8088;

    /**
     * Socket服务器
     */
    private static ServerSocket socketServer = null;

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
            while (true) {
                try {
                    Socket tempSocket = socketServer.accept();
                    tempSocket.setSoTimeout(600 * 1000);
                    OnAGwDataServiceImpl onGwDataServiceImpl = new OnAGwDataServiceImpl();
                    onGwDataServiceImpl.setSocket(tempSocket);
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void start() {
        try {
            new Thread(this).start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
