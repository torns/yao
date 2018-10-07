package com.y3tu.cloud.daemon.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.y3tu.cloud.daemon.entity.StatusTraceLog;
import com.y3tu.cloud.daemon.mapper.StatusTraceLogMapper;
import com.y3tu.cloud.daemon.service.StatusTraceLogService;
import org.springframework.stereotype.Service;

/**
 * @author lengleng
 * @date 2018年08月16日
 * elastic-job 任务轨迹处理
 */
@Service("statusTraceLogService")
public class StatusTraceLogServiceImpl extends ServiceImpl<StatusTraceLogMapper, StatusTraceLog> implements StatusTraceLogService {

}
