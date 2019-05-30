package com.y3tu.cloud.log.server.service.impl;

import com.y3tu.cloud.log.server.model.entity.Log;
import com.y3tu.cloud.log.server.service.LogService;
import com.y3tu.cloud.log.server.mapper.LogMapper;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统日志服务实现类
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {

}
