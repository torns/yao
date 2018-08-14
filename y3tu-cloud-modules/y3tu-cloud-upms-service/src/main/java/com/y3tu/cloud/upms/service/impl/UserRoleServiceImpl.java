package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.dao.UserRoleDao;
import com.y3tu.cloud.upms.entity.Role;
import com.y3tu.cloud.upms.entity.UserRole;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-07
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    @Override
    public List<Role> findByUserId(String id) {
        return baseMapper.findByUserId(id);
    }


    @Override
    public void insertUserRole(UserRole userRole) {
        this.insert(userRole);
    }


}
