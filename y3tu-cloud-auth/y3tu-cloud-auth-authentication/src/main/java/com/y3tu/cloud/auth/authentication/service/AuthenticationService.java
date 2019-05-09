package com.y3tu.cloud.auth.authentication.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author y3tu
 * @date 2019-05-09
 */
public interface AuthenticationService {
    /**
     * 校验权限
     *
     * @param authRequest
     * @return 是否有权限
     */
    boolean decide(HttpServletRequest authRequest);

}
