package com.on.GWData.run;

import com.on.schedule.service.DealDataService;
import com.on.schedule.service.JobService;
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

    @Scheduled(cron="0 0/10 * * * ? ")
    public void run(){
        if(!IPUtil.needRunTask()){
            mLogger.info("本机不启用AutoRun-DealData");
            return ;
        }
        jobService.start();
    }

}
