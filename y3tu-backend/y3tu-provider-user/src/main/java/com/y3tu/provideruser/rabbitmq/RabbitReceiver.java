package com.y3tu.provideruser.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author y3tu
 * @date 2018/5/11
 */
@Component
@RabbitListener(queues = "hello")
public class RabbitReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.print("Receiver:" + hello);
    }
}
