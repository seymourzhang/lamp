package com.on.schedule.run;

import com.on.schedule.service.Impl.DealDataServiceImpl;
import com.on.schedule.service.OnADealDataService;
import com.on.util.common.IPUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("DealAutoRun")
public class AutoRun {

    private static Logger mLogger = Logger.getLogger(AutoRun.class);

    @Autowired
    private OnADealDataService onADealDataService;

    @Scheduled(cron="0 0/5 * * * ?")
    public void run1() {
        try {
            if (!IPUtil.needRunTask()) {
                mLogger.info("本机不启用AutoRun-DealData");
                return;
            }
            DealDataServiceImpl temp = new DealDataServiceImpl();
            temp.setOnADealDataService(onADealDataService);
            Thread thread = new Thread(temp);
            thread.start();
            thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
