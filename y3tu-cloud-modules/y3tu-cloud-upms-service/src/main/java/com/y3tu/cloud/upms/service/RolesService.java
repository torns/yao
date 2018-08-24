package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.Roles;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
public interface RolesService extends BaseService<Roles> {
    /**
     * 根据用户id查询用户拥有的角色
     * @param userId
     * @return
     */
    List<Roles> findByUserId(long userId);
}
