package com.y3tu.cloud.auth.authorization.security;

import com.y3tu.cloud.auth.authorization.service.UserService;
import com.y3tu.cloud.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO = userService.loadUserByUsername(username);
        if (userVO == null) {
            throw new UsernameNotFoundException("未查询到此用户");
        }
        return new UserDetailsImpl(userVO);
    }
}
