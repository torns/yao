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
	User findByUsernameAndStatus(String username);
	List<User> selectAll();

}
