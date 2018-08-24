package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.Menus;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
public interface MenusService extends BaseService<Menus> {
    /**
     * 根据用户id查询用户拥有的菜单
     * @param userId
     * @return
     */
    List<Menus> findByUserId(long userId);
}
