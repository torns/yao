package com.y3tu.cloud.gateway.feign;

import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.gateway.feign.fallback.AuthenticationServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @author y3tu
 * @date 2019-05-09
 */
@FeignClient(name = ServiceNameConstants.AUTHENTICATION_SERVER, fallback = AuthenticationServiceFallbackImpl.class)
public interface AuthenticationService {

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return <pre>
     * </pre>
     */
    @PostMapping(value = "/auth/hasPermission")
    boolean hasPermission(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);
}
