package com.y3tu.cloud.log.server.listener;

import com.rabbitmq.client.Channel;
import com.y3tu.cloud.log.server.model.entity.EsLog;
import com.y3tu.cloud.log.server.service.EsLogService;
import com.y3tu.cloud.log.starter.constant.LogQueueNameConstant;
import com.y3tu.cloud.log.starter.model.dto.LogDTO;
import com.y3tu.cloud.log.server.model.entity.Log;
import com.y3tu.cloud.log.server.service.LogService;
import com.y3tu.tool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听日志存储请求
 */
@Slf4j
@Component
public class LogReceiveListener {

    @Autowired
    private LogService logService;
    @Autowired
    private EsLogService esLogService;


    /**
     * 日志队列消费端，存数据库
     *
     * @param logDTO
     * @param channel
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = LogQueueNameConstant.DB_LOG_QUEUE)
    public void handlerDB(LogDTO logDTO, Channel channel, Message message){
        // 确认消息接受
        Log log = new Log();
        BeanUtils.copyProperties(logDTO, log);
        log.setCreateTime(DateUtil.date());
        logService.save(log);
        LogReceiveListener.log.info("系统日志消费端成功消费信息：log={}", logDTO);

    }


    /**
     * 日志队列消费端，存elasticsearch
     *
     * @param logDTO
     * @param channel
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = LogQueueNameConstant.ES_LOG_QUEUE)
    public void handlerEs(LogDTO logDTO, Channel channel, Message message) {
        // 确认消息接受
        EsLog esLog = new EsLog();
        BeanUtils.copyProperties(logDTO, esLog);
        esLog.setCreateTime(DateUtil.date());
        esLogService.saveLog(esLog);
        LogReceiveListener.log.info("系统日志消费端成功消费信息：log={}", logDTO);
    }


}
