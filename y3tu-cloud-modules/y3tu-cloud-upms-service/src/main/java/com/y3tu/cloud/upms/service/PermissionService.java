package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.Permission;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;


/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * 根据用户Id获取这个用户所具有的权限
     * @param userId
     * @return
     */
    List<Permission> findByUserId(String userId);

    /**
     * 根据角色名称获取这个角色所具有的权限
     *
     * @param roleName
     * @return
     */
    List<Permission> findByRoleName(String roleName);
}
