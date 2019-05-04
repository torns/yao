package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.mapper.UserRoleMapper;
import com.y3tu.cloud.upms.model.entity.Role;
import com.y3tu.cloud.upms.model.entity.UserRole;
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
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<Role> findByUserId(String id) {
        return baseMapper.findByUserId(id);
    }


    @Override
    public void insertUserRole(UserRole userRole) {
        this.save(userRole);
    }

    @Override
    public List<String> findDepIdsByUserId(String userId) {
        return null;
    }


}
