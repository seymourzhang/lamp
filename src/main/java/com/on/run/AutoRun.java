package com.on.run;

import com.on.schedule.DealDataService;
import com.on.schedule.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("AutoRun")
public class AutoRun {

    @Autowired
    private JobService jobService;

    @Autowired
    private DealDataService dealDataService;

    @Scheduled(cron="0 0/10 * * * ? ")
    public void run(){
        jobService.start();
    }

    @Scheduled(cron="0 0/5 * * * ?")
    public void run1() {
        dealDataService.start();
    }

}
