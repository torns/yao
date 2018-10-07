package com.y3tu.cloud.daemon.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.y3tu.cloud.daemon.entity.ExecutionLog;
import com.y3tu.cloud.daemon.mapper.ExecutionLogMapper;
import com.y3tu.cloud.daemon.service.ExecutionLogService;
import org.springframework.stereotype.Service;

/**
 * @author lengleng
 * @date 2018年08月16日
 * elastic-job 任务日志处理
 */
@Service("executionLogService")
public class ExecutionLogServiceImpl extends ServiceImpl<ExecutionLogMapper, ExecutionLog> implements ExecutionLogService {

}
