package com.on.run;

import com.on.schedule.DealDataService;
import com.on.schedule.JobService;
import com.on.util.common.IPUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("AutoRun")
public class AutoRun {
    private static Logger mLogger = Logger.getLogger(AutoRun.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private DealDataService dealDataService;

    /*@Scheduled(cron="0 0/10 * * * ? ")
    public void run(){
        jobService.start();
    }

    @Scheduled(cron="0 0/5 * * * ?")
    public void run1() {
        if(!IPUtil.needRunTask()){
            mLogger.info("本机不启用AutoRun-DealData");
            return ;
        }
        dealDataService.start();
    }*/

}
