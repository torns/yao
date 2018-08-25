package com.y3tu.cloud.auth.authentication.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AuthenticationService {
    /**
     * 校验权限
     *
     * @param authRequest
     * @return 是否有权限
     */
    boolean decide(HttpServletRequest authRequest);

}
