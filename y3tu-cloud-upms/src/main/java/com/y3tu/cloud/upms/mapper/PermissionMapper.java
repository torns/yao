package com.y3tu.cloud.upms.mapper;

import com.y3tu.cloud.upms.model.entity.Permission;
import com.y3tu.tool.web.base.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 权限表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date  2018-08-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户Id获取这个用户所具有的权限
     * @param id
     * @return
     */
    List<Permission> findByUserId(String id);
}