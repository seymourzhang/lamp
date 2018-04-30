package com.on.GWData.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.on.GWData.Entity.OnADealData;
import com.on.GWData.Entity.OnAGWData;
import com.on.GWData.repository.OnADealDataRepository;
import com.on.GWData.repository.OnAGWDataRepository;
import com.on.GWData.service.OnAGwDataService;
import com.on.util.common.StringHexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class OnAGwDataServiceImpl implements OnAGwDataService, Runnable {

    private static final Logger mLogger = LoggerFactory.getLogger(OnAGwDataServiceImpl.class);

    private Socket socket = null;

    public void setSocket(Socket socket){this.socket = socket;}

    private OnAGWDataRepository onAGWDataRepository = null;

    public void setOnAGWDataRepository(OnAGWDataRepository onAGWDataRepository){this.onAGWDataRepository = onAGWDataRepository;}

    @Autowired
    private OnAGWDataRepository gwDataDao;

    @Autowired
    private OnADealDataRepository onADealDataRepository;

    /**
     * Socket服务器
     */
    private static ServerSocket socketServer = null;

    @Override
    public void run() {
        long socketThreadId = 0;
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

                String hexString = StringHexUtil.bytes2HexString(res);
                mLogger.info(sdf.format(new Date(longtime))+"接收帧"+dataCount+"："+ hexString);

                Date now = new Date(longtime);
                System.out.println("now time is :" + now);
                OnAGWData gwData = new OnAGWData();
                gwData.setCollectionDatetime(now);
                gwData.setData(hexString);
                gwData.setImei("seymourTest");
                gwData.setCreateDateTime(now);
                gwData.setModifyDateTime(now);
                onAGWDataRepository.save(gwData);
                out = new DataOutputStream(socket.getOutputStream());
                String outPutStr = "Except Data Ok";
                out.writeUTF(outPutStr.substring(0, outPutStr.length() - 1));
                mLogger.info("");
                dataCount++;
            }
            if (out != null) out.close();
            in.close();
            mLogger.info("BBFarTask("+socketTaskName+") end   ,线程号====" + Thread.currentThread());
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
//            SocketManage.getInstance().removeSocket(BBFarConstants.DEVICE_TYPE,deviceCode,socketThreadId);
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public List<OnADealData> findByOrder() {
        Pageable pageable = new PageRequest(0, 200, Sort.Direction.DESC, "id");
//        List<OnAGWData> datas = gwDataDao.findByOrder(pageable);
        List<OnADealData> datas = onADealDataRepository.findAllByImei("");
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
