package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.Users;
import com.y3tu.tool.web.base.service.BaseService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
public interface UsersService extends BaseService<Users> {
    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    Users findUserByUsername(String username);

    /**
     * 通过手机号获取用户
     *
     * @param mobile
     * @return
     */
    Users findUserByMobile(String mobile);
}
