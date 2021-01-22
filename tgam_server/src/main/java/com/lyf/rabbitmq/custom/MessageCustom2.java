package com.lyf.rabbitmq.custom;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "message-queue")
public class MessageCustom2 {

    @RabbitHandler
    public void process(String testQueue){
        System.out.println("receiver2"+testQueue);
    }


}
