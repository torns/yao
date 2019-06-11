package com.y3tu.yao.upms.mapper;

import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.tool.web.base.mapper.BaseMapper;

import java.util.List;

/**
 * 资源Mapper
 *
 * @author y3tu
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 根据角色code查询资源集合
     * @param roleCode
     * @return
     */
    List<Resource> findResourceByRoleCode(String roleCode);
}