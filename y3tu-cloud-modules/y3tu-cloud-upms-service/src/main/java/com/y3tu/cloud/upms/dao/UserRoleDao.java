package com.y3tu.cloud.upms.dao;

import com.y3tu.cloud.upms.entity.Role;
import com.y3tu.cloud.upms.entity.UserRole;
import com.y3tu.tool.web.base.dao.BaseMapper;

import java.util.List;

/**
 * <p>
  * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date  2018-08-07
 */
public interface UserRoleDao extends BaseMapper<UserRole> {
    /**
     * 根据用户Id获取用户所属角色
     * @param id
     * @return
     */
    List<Role> findByUserId(String id);
}