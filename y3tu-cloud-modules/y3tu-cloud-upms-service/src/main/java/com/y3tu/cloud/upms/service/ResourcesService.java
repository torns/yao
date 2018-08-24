package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.Resources;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
public interface ResourcesService extends BaseService<Resources> {
    /**
     * 根据用户id查询用户拥有的资源
     * @param userId
     * @return
     */
	List<Resources> findByUserId(long userId);

    /**
     * 根据角色编码查询角色拥有的资源
     * @param roleCode
     * @return
     */
	List<Resources> findByRoleCode(String roleCode);
}
