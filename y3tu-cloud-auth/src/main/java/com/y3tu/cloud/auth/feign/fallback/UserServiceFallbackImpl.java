package com.y3tu.cloud.auth.feign.fallback;

import com.y3tu.cloud.auth.feign.UserService;
import com.y3tu.cloud.common.vo.UsersVO;
import com.y3tu.tool.web.base.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author y3tu
 * 用户服务的fallback
 */
@Service
@Slf4j
public class UserServiceFallbackImpl implements UserService {

    @Override
    public UsersVO findUserByUsername(String username) {
        log.error("调用{}异常:{}", "findUserByUsername", username);
        return null;
    }

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return UserVo
     */
    @Override
    public UsersVO findUserByMobile(String mobile) {
        log.error("调用{}异常:{}", "通过手机号查询用户", mobile);
        return null;
    }

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     * @return UserVo
     */
    @Override
    public UsersVO findUserByOpenId(String openId) {
        log.error("调用{}异常:{}", "通过OpenId查询用户", openId);
        return null;
    }

    @Override
    public R findPermissionByRole(String role) {
        log.error("调用{}异常{}", "findPermissionByRole", role);
        return R.error();
    }
}
