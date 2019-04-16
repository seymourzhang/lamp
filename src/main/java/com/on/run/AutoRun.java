package com.on.run;

import com.on.schedule.service.DealDataService;
import com.on.schedule.service.Impl.DealDataServiceImpl;
import com.on.schedule.service.JobService;
import com.on.schedule.service.OnADealDataService;
import com.on.util.common.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("AutoRun")
public class AutoRun {
    private static Logger mLogger = LoggerFactory.getLogger(AutoRun.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private OnADealDataService onADealDataService;


    @Scheduled(cron="0 0/10 * * * ? ")
    public void run(){
        if(IPUtil.needRunTask()){
            mLogger.info("本机不启用AutoRun-DealData");
            return ;
        }
        jobService.start();
    }

    @Scheduled(cron="0 0/5 * * * ?")
    public void run1() {
        try {
            if (IPUtil.needRunTask()) {
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
