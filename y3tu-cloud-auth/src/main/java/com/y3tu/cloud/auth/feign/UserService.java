package com.y3tu.cloud.auth.feign;


import com.y3tu.cloud.auth.feign.fallback.UserServiceFallbackImpl;
import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.common.vo.SysUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author y3tu
 */
@FeignClient(name = ServiceNameConstants.UPMS, fallback = UserServiceFallbackImpl.class)
public interface UserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/user/findUserByUsername/{username}")
    SysUserVO findUserByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return UserVo
     */
    @GetMapping("/user/findUserByMobile/{mobile}")
    SysUserVO findUserByMobile(@PathVariable("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     * @return UserVo
     */
    @GetMapping("/user/findUserByOpenId/{openId}")
    SysUserVO findUserByOpenId(@PathVariable("openId") String openId);
}
