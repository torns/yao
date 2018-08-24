package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.y3tu.cloud.upms.entity.Menus;
import com.y3tu.cloud.upms.entity.Resources;
import com.y3tu.cloud.upms.entity.Roles;
import com.y3tu.cloud.upms.entity.Users;
import com.y3tu.cloud.upms.dao.UsersDao;
import com.y3tu.cloud.upms.service.*;
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
 * @since 2018-08-24
 */
@Service
public class UsersServiceImpl extends BaseServiceImpl<UsersDao, Users> implements UsersService {
    @Autowired
    RolesService rolesService;
    @Autowired
    ResourcesService resourcesService;
    @Autowired
    MenusService menusService;
    @Override
    public Users findUserByUsername(String username) {
        List<Users> list = this.selectList(new EntityWrapper<Users>().eq("username", username).eq("enabled", "1"));
        if (list != null && list.size() > 0) {
            Users users = list.get(0);
            return buildUsers(users);
        }
        return null;
    }

    @Override
    public Users findUserByMobile(String mobile) {
        List<Users> list = this.selectList(new EntityWrapper<Users>().eq("mobile", mobile).eq("enabled", "1"));
        if (list != null && list.size() > 0) {
            Users users = list.get(0);
            return buildUsers(users);
        }
        return null;
    }

    /**
     * 给user封装其对应的角色和权限
     * @param user
     * @return
     */
    private Users buildUsers(Users user) {
        List<Roles> roleList = rolesService.findByUserId(user.getId());
        user.setRolesList(roleList);
        List<Resources> resourcesList = resourcesService.findByUserId(user.getId());
        user.setResourcesList(resourcesList);
        List<Menus> menusList = menusService.findByUserId(user.getId());
        user.setMenusList(menusList);
        return user;
    }
}
