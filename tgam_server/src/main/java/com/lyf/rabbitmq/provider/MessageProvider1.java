package com.lyf.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageProvider1 {


    @Autowired
    AmqpTemplate rabbitmqTemplate;

    @RequestMapping("/send")
    public void send(String content){
     this.rabbitmqTemplate.convertAndSend("message-queue","hello");
    }


}
