package com.y3tu.cloud.auth.authorization.service;


import com.y3tu.cloud.auth.authorization.exception.AuthException;
import com.y3tu.cloud.auth.authorization.feign.UserService;
import com.y3tu.cloud.auth.authorization.util.UserDetailsImpl;
import com.y3tu.cloud.common.vo.UsersVO;
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
        UsersVO userVo = userService.findUserByUsername(username);
        return new UserDetailsImpl(userVo);
    }
}
