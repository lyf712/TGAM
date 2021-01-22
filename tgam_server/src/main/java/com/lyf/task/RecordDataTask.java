package com.lyf.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RecordDataTask {
    private int count=1;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Bean
    @Scheduled(fixedRate = 60000)
    public void task1(){

        System.out.println(count);
        count++;
    }




}
