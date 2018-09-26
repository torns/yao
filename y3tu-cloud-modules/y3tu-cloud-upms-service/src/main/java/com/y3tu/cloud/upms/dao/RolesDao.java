package com.y3tu.cloud.upms.dao;

import com.y3tu.cloud.upms.entity.Roles;
import com.y3tu.tool.web.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
public interface RolesDao extends BaseMapper<Roles> {
    /**
     * 根据用户id查询用户拥有的角色
     *
     * @param userId
     * @return
     */
    List<Roles> findByUserId(@Param("userId") long userId);
}