package com.y3tu.cloud.upms.service.impl;


import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.cloud.upms.dao.SysLogMapper;
import com.y3tu.cloud.upms.model.entity.SysLog;
import com.y3tu.cloud.upms.service.SysLogService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author liuht
 * @since 2017-11-20
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public Boolean updateByLogId(Long id) {
        SysLog sysLog = new SysLog();
        sysLog.setId(id);
        sysLog.setDelFlag(CommonConstant.STATUS_DEL);
        sysLog.setUpdateTime(new Date());
        return updateById(sysLog);
    }
}
