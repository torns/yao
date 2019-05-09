package com.y3tu.cloud.auth.authorization.controller;

import com.y3tu.tool.core.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @DeleteMapping("/token/{token}")
    public R<Boolean> removeAccessToken(@PathVariable("token") String token) {
        return new R<>(consumerTokenServices.revokeToken(token));
    }

    /**
     * 用户信息校验
     *
     * @param authentication 信息
     * @return 用户信息
     */
    @RequestMapping("/user")
    public Object user(Authentication authentication) {
        return authentication.getPrincipal();
    }

}
