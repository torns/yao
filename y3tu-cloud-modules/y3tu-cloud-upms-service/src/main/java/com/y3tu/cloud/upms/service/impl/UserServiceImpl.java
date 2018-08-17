package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.y3tu.cloud.upms.dao.UserDao;
import com.y3tu.cloud.upms.entity.Permission;
import com.y3tu.cloud.upms.entity.Role;
import com.y3tu.cloud.upms.entity.User;
import com.y3tu.cloud.upms.service.PermissionService;
import com.y3tu.cloud.upms.service.RoleService;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.cloud.upms.service.UserService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserRoleService userRoleService;

    @Override
    public User findUserByUsername(String username) {
        List<User> list = this.selectList(new EntityWrapper<User>().eq("username", username).eq("status", "0"));
        if (list != null && list.size() > 0) {
            User user = list.get(0);
            return buildUserRoleAndPermission(user);
        }
        return null;
    }

    @Override
    public User findUserByMobile(String mobile) {
        List<User> list = this.selectList(new EntityWrapper<User>().eq("mobile", mobile).eq("status", "0"));
        if (list != null && list.size() > 0) {
            User user = list.get(0);
            return buildUserRoleAndPermission(user);
        }
        return null;
    }

    /**
     * 给user封装其对应的角色和权限
     * @param user
     * @return
     */
    private User buildUserRoleAndPermission(User user) {
        List<Role> roleList = userRoleService.findByUserId(user.getId());
        user.setRoles(roleList);
        List<Permission> permissionList = permissionService.findByUserId(user.getId());
        user.setPermissions(permissionList);
        return user;
    }
}
