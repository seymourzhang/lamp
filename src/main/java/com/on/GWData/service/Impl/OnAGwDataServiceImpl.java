package com.on.GWData.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.on.GWData.Entity.OnADealData;
import com.on.GWData.Entity.OnAGWData;
import com.on.GWData.repository.OnADealDataRepository;
import com.on.GWData.repository.OnAGWDataRepository;
import com.on.GWData.service.DeviceService;
import com.on.GWData.service.OnAGwDataService;
import com.on.util.common.PageData;
import com.on.util.common.StringHexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
@Transactional(readOnly = true)
public class OnAGwDataServiceImpl implements OnAGwDataService, Runnable {

    private static final Logger mLogger = LoggerFactory.getLogger(OnAGwDataServiceImpl.class);

    private Socket socket = null;

    public void setSocket(Socket socket){this.socket = socket;}

    private OnAGWDataRepository onAGWDataRepository = null;

    public void setOnAGWDataRepository(OnAGWDataRepository onAGWDataRepository){this.onAGWDataRepository = onAGWDataRepository;}

    private OnAGwDataService onAGwDataService = null;

    public void setDeviceService(DeviceService deviceService) {this.deviceService = deviceService;}

    private DeviceService deviceService;

    @Autowired
    private OnAGWDataRepository gwDataDao;

    @Autowired
    private OnADealDataRepository onADealDataRepository;

    /**
     * Socket服务器
     */
    private static ServerSocket socketServer = null;

    private final Lock queueLock=new ReentrantLock();

    @Override
    @Transactional
    public void run() {
        long socketThreadId = 0;
        queueLock.lock();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
            String socketTaskName = sdf.format(new Date()) ;
            mLogger.info("BBFarTask("+socketTaskName+") start ,线程号====" + Thread.currentThread());

            socketThreadId = Thread.currentThread().getId();
            int dataCount = 1;
            InputStream in = socket.getInputStream();
            ByteArrayOutputStream bo ;
            DataOutputStream out = null;
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length=in.read(buffer)) != -1) {
                bo = new ByteArrayOutputStream();
                bo.write(buffer, 0, length);
                long longtime = System.currentTimeMillis();
                byte[] res = bo.toByteArray();
                String strRead = new String(res);
                String strRead1 = String.copyValueOf(strRead.toCharArray(), 0, res.length);
                mLogger.info("resStr format:" + strRead);
                mLogger.info("resStr format:" + strRead1);
                String hexString = StringHexUtil.bytes2HexString(res);
                mLogger.info(sdf.format(new Date(longtime))+"接收帧"+dataCount+"："+ strRead1);
//                String imeiNo = StringHexUtil.convertHexToString(strRead1.substring(0, 30));
//                String dataExp = strRead1.substring(32, 52);
                Date now = new Date(longtime);
                System.out.println("now time is :" + now);
                System.out.println("strRead1 length is:" + strRead1.length());
                String imeiNo = strRead1.substring(0, 15);
                if (strRead1.length() == 36) {
                    String dataExp = strRead1.substring(16, 36);
                    OnAGWData gwData = new OnAGWData();
                    gwData.setCollectionDatetime(now);
                    gwData.setData(dataExp);
                    gwData.setImei(imeiNo);
                    gwData.setCreateDateTime(now);
                    gwData.setModifyDateTime(now);
                    OnAGWData a = deviceService.insert(gwData);
                    mLogger.info("insert after object : " + a.getId());
//                    onAGWDataRepository.saveAndFlush(gwData);
                } else {
                    String dataExp = hexString.substring(32, hexString.length());
                    System.out.println("behind cut data :" + dataExp);
                    if (dataExp.length() == 20) {
                        OnAGWData gwData = new OnAGWData();
                        gwData.setCollectionDatetime(now);
                        gwData.setData(dataExp);
                        gwData.setImei(imeiNo);
                        gwData.setCreateDateTime(now);
                        gwData.setModifyDateTime(now);
//                        onAGWDataRepository.saveAndFlush(gwData);
                        OnAGWData a = deviceService.insert(gwData);
                        mLogger.info("insert after object : " + a.getId());
                    }
                }
                mLogger.info("");
                dataCount++;
            }
            in.close();
            mLogger.info("BBFarTask("+socketTaskName+") end   ,线程号====" + Thread.currentThread());
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
//            SocketManage.getInstance().removeSocket(BBFarConstants.DEVICE_TYPE,deviceCode,socketThreadId);
            queueLock.unlock();
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public List<OnADealData> findByOrder(PageData pd) {
        Pageable pageable = new PageRequest(0, 5000, Sort.Direction.DESC, "id");
//        List<OnAGWData> datas = gwDataDao.findByOrder(pageable);
        List<OnADealData> datas = onADealDataRepository.findAllByImei(pd.getString("imei_no"));
        return datas;
    }

    public OnAGWData findReqDealData() {
        return null;
    }

    public JSONObject dealResultHexValue(byte[] recvData) {
        JSONObject result = new JSONObject();

        return result;
    }

}
