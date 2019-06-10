package com.y3tu.yao.upms.service;

import com.y3tu.yao.common.vo.UserVO;
import com.y3tu.yao.upms.model.dto.UserDTO;
import com.y3tu.yao.upms.model.entity.User;
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
     *
     * @param username
     * @return
     */
    UserDTO findByUsernameAndStatus(String username);

    /**
     * 通过手机号查询用户
     *
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 通过邮箱查询用户信息
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 查询全量用户信息
     *
     * @return
     */
    List<UserDTO> selectAll();

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户名
     * @return userVo
     */
    UserVO findUserById(String userId);

    /**
     * 根据用户名查询用户角色信息
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO findUserByUsername(String username);

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserVO findUserByMobile(String mobile);

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    UserVO findUserByOpenId(String openId);

}
