package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.model.entity.Permission;
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
     * @param id
     * @return
     */
    List<Permission> findByUserId(String id);


    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

}
