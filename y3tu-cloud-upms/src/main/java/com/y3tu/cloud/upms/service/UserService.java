package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.model.dto.UserDTO;
import com.y3tu.cloud.upms.model.entity.User;
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
     * 通过用户名查询有效用户
     * @param username
     * @return
     */
    UserDTO findByUsernameAndStatus(String username);

    /**
     * 通过手机号查询用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 通过邮箱查询用户信息
     * @param email
     * @return
     */
    User findByEmail(String email);
    /**
     * 查询全量用户信息
     * @return
     */
    List<UserDTO> selectAll();

}
