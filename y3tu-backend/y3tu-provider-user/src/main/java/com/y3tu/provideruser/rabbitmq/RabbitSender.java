package com.y3tu.provideruser.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author y3tu
 * @date 2018/5/11
 */
@Component
public class RabbitSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.print("Send " + context);
        amqpTemplate.convertAndSend("hello", context);
    }
}
