package com.y3tu.cloud.auth.service;


import com.y3tu.cloud.auth.feign.UserService;
import com.y3tu.cloud.auth.util.UserDetailsImpl;
import com.y3tu.cloud.common.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author y3tu
 * <p>
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserVO userVo = userService.findUserByUsername(username);
        return new UserDetailsImpl(userVo);
    }
}
