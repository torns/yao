package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.entity.User;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
public interface UserService extends BaseService<User> {
	/**
	 * 通过用户名获取用户
	 * @param username
	 * @return
	 */
	User findUserByUsername(String username);

	/**
	 * 通过手机号获取用户
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);

}
