package com.y3tu.provideruser.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author y3tu
 * @date 2018/5/11
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue helloQueue(){
        return  new Queue("hello");
    }
}
