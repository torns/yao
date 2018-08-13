package com.y3tu.cloud.auth.service;


import com.y3tu.cloud.auth.feign.UserService;
import com.y3tu.cloud.auth.util.UserDetailsImpl;
import com.y3tu.cloud.common.vo.UserVO;
import com.y3tu.tool.core.lang.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author lengleng
 * @date 2017/10/26
 * <p>
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Console.log("test");
        UserVO userVo = userService.findUserByUsername(username);
        return new UserDetailsImpl(userVo);
    }
}
