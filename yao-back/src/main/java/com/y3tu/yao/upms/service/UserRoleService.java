package com.y3tu.yao.upms.service;

import com.y3tu.yao.upms.model.entity.Role;
import com.y3tu.yao.upms.model.entity.UserRole;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author y3tu
 */
public interface UserRoleService extends BaseService<UserRole> {

    /**
     * 根据用户Id获取用户所属角色
     *
     * @param id
     * @return
     */
    List<Role> findByUserId(String id);

    void insertUserRole(UserRole userRole);

    /**
     * 根据用户Id获取用户所属部门
     *
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(String userId);
}
