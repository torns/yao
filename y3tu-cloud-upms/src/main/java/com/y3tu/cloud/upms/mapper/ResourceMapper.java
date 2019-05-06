package com.y3tu.cloud.upms.mapper;

import com.y3tu.cloud.upms.model.entity.Resource;
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
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 根据角色code查询资源集合
     * @param roleCode
     * @return
     */
    List<Resource> findResourceByRoleCode(String roleCode);
}