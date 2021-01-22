package com.lyf.task;

import com.lyf.task.detail.Runnable1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
@EnableScheduling
public class DynamicTask {


    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?>future1;

    private ScheduledFuture<?>future2;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }


    public boolean startCron1(){

        // CronExpression :https://www.cnblogs.com/personblog/p/11245992.html

        future1 =threadPoolTaskScheduler.schedule(new Runnable1(), new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger("0/10 * * * * ?").nextExecutionTime(triggerContext);
            }
        });
        return true;
    }

    public boolean stopCron1(){
        if(future1!=null){
            future1.cancel(true);
        }
        System.out.println("关闭线程一");
        return true;
    }



}
