package com.y3tu.cloud.upms.dao;

import com.y3tu.cloud.upms.entity.Menus;
import com.y3tu.tool.web.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
public interface MenusDao extends BaseMapper<Menus> {
    /**
     * 根据用户id查询用户拥有的菜单
     *
     * @param userId
     * @return
     */
    List<Menus> findByUserId(@Param("userId") long userId);
}