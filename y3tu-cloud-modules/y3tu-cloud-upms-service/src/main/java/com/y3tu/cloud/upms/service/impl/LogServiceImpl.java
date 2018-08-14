package com.y3tu.cloud.upms.service.impl;


import com.y3tu.cloud.upms.dao.LogDao;
import com.y3tu.cloud.upms.entity.Log;
import com.y3tu.cloud.upms.entity.Role;
import com.y3tu.cloud.upms.entity.UserRole;
import com.y3tu.cloud.upms.service.LogService;
import com.y3tu.cloud.upms.service.RoleService;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.tool.core.time.DateUtil;
import com.y3tu.tool.core.util.SnowFlakeUtil;
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
public class LogServiceImpl extends BaseServiceImpl<LogDao, Log> implements LogService {

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test() throws Exception {
        UserRole userRole = new UserRole();
        userRole.setId(SnowFlakeUtil.getFlowIdInstance().nextId() + "");
       // userRole.setId("40561275398262784");
        userRole.setCreateTime(DateUtil.getNowDate());

        Role role = new Role();
        role.setId(SnowFlakeUtil.getFlowIdInstance().nextId() + "");
        role.setId("40561904703246337");
        role.setCreateTime(DateUtil.getNowDate());

        userRoleService.insertUserRole(userRole);
        roleService.insertTestTwo(role);
    }
}
