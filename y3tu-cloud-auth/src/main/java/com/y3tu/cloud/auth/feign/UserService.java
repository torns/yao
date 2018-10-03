package com.y3tu.cloud.auth.feign;


import com.y3tu.cloud.auth.feign.fallback.UserServiceFallbackImpl;
import com.y3tu.cloud.common.vo.UsersVO;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author y3tu
 */
@Component
@FeignClient(name = "y3tu-cloud-upms-service", fallback = UserServiceFallbackImpl.class)
public interface UserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return UserVo
     */
    @GetMapping("/upms/users/findUserByUsername")
    UsersVO findUserByUsername(@RequestParam("username") String username);

    /**
     * 通过手机号查询用户、角色信息
     *
     * @param mobile 手机号
     * @return UserVo
     */
    @GetMapping("/upms/users/findUserByMobile")
    UsersVO findUserByMobile(@RequestParam("mobile") String mobile);

    /**
     * 根据OpenId查询用户信息
     *
     * @param openId openId
     * @return UserVo
     */
    @GetMapping("/users/findUserByOpenId/{openId}")
    UsersVO findUserByOpenId(@PathVariable("openId") String openId);

    /**
     * 通过角色名查询菜单
     *
     * @param roleName 角色名称
     * @return 权限列表
     */
    @GetMapping(value = "/upms/permission/findPermissionByRole/{roleName}")
    R findPermissionByRole(@PathVariable("roleName") String roleName);
}
