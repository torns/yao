package com.y3tu.cloud.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cloud.log.mapper.SysLogMapper;
import com.y3tu.cloud.log.model.entity.SysLog;
import com.y3tu.cloud.log.model.query.SysLogQuery;
import com.y3tu.cloud.log.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 系统日志服务实现类
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysLogQuery pageByQuery(SysLogQuery query) {
        query.setDesc("create_time");
        sysLogMapper.pageByQuery(query);
        return query;
    }
}
