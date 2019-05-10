package com.y3tu.cloud.auth.authorization.controller;

import com.y3tu.tool.core.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @DeleteMapping("/token/{token}")
    public R<Boolean> removeAccessToken(@PathVariable("token") String token) {
        return new R<>(consumerTokenServices.revokeToken(token));
    }

}
