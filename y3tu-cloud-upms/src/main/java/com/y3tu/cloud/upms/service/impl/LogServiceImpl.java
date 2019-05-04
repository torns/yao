package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.mapper.LogMapper;
import com.y3tu.cloud.upms.model.entity.Log;
import com.y3tu.cloud.upms.service.LogService;
import com.y3tu.cloud.upms.service.RoleService;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test() throws Exception {

    }
}
