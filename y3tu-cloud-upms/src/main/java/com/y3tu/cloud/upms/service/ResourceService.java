package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.model.dto.ResourceTreeDTO;
import com.y3tu.cloud.upms.model.entity.Resource;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
public interface ResourceService extends BaseService<Resource> {
    /**
     * 根据角色codes查询菜单树形
     *
     * @param roleCodes
     * @return
     */
    List<ResourceTreeDTO> getMenuTreeByRoleCodes(List<String> roleCodes);

    /**
     * 根据角色codes查询菜单列表
     *
     * @param roleCodes
     * @return
     */
    Set<Resource> getResourceRoleCodes(List<String> roleCodes);

    /**
     * 查询所有的资源
     *
     * @return
     */
    List<ResourceTreeDTO> getAllResourceTree();

    /**
     * 删除资源以及子资源
     *
     * @param id
     * @return
     */
    Boolean deleteResource(Integer id);

    /**
     * 根据角色code查询资源信息
     *
     * @param roleCode
     * @return
     */
    List<Resource> findResourceByRoleCode(String roleCode);

    List<String> findPermission(List<String> roles);

}
