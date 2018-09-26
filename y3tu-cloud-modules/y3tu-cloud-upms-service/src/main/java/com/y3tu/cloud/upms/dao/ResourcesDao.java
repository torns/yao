package com.y3tu.cloud.upms.dao;

import com.y3tu.cloud.upms.entity.Resources;
import com.y3tu.tool.web.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
public interface ResourcesDao extends BaseMapper<Resources> {
    /**
     * 根据用户id查询用户拥有的资源
     *
     * @param userId
     * @return
     */
    List<Resources> findByUserId(@Param("userId") long userId);

    /**
     * 根据角色编码查询角色拥有的资源
     *
     * @param roleCode
     * @return
     */
    List<Resources> findByRoleCode(@Param("roleCode") String roleCode);
}