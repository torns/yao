package com.y3tu.cloud.log.listener;

import com.rabbitmq.client.Channel;
import com.y3tu.cloud.common.constants.MqQueueNameConstants;
import com.y3tu.cloud.log.model.dto.LogDTO;
import com.y3tu.cloud.log.model.entity.Log;
import com.y3tu.cloud.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 监听日志存储请求
 */
@Slf4j
@Component
public class LogReceiveListener {

    @Autowired
    private LogService logService;


    /**
     * 日志队列消费端，存数据库
     *
     * @param logDTO
     * @param channel
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = MqQueueNameConstants.LOG_QUEUE)
    public void handler(LogDTO logDTO, Channel channel, Message message) throws IOException {
        LogReceiveListener.log.info("系统日志消费端成功消费信息：log={}", logDTO);
        // 确认消息接受
        Log log = new Log();
        BeanUtils.copyProperties(logDTO, log);
        log.setCreateTime(new Date());
        logService.save(log);
    }


}
