package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.Role;
import com.y3tu.tool.web.base.service.BaseService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
public interface RoleService extends BaseService<Role> {

    public boolean insertTestOne(Role entity)throws Exception;
    public boolean insertTestTwo(Role entity)throws Exception;
}
