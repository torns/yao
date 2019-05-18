package com.y3tu.cloud.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cloud.log.mapper.LogMapper;
import com.y3tu.cloud.log.model.entity.Log;
import com.y3tu.cloud.log.model.query.LogQuery;
import com.y3tu.cloud.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 系统日志服务实现类
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public LogQuery pageByQuery(LogQuery query) {
        query.setDesc("create_time");
        logMapper.pageByQuery(query);
        return query;
    }
}
