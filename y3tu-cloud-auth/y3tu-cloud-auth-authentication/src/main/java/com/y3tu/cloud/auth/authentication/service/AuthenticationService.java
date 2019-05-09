package com.y3tu.cloud.auth.authentication.service;


import org.springframework.security.jwt.Jwt;

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

    /**
     * 从认证信息中提取jwt token 对象
     *
     * @param authentication 认证信息  Authorization: bearer header.payload.signature
     * @return Jwt对象
     */
    Jwt getJwt(String authentication);

    /**
     * 是否无效authentication
     *
     * @param authentication
     * @return
     */
    boolean invalidJwtAccessToken(String authentication);

    /**
     * 判断url是否在忽略的范围内
     * 只要是配置中的开头，即返回true
     *
     * @param url
     * @return
     */
    boolean ignoreAuthentication(String url);

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authRequest
     */
    boolean hasPermission(HttpServletRequest authRequest);
}
