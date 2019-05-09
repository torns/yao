package com.y3tu.cloud.gateway.feign;

import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.gateway.feign.fallback.AuthenticationServiceFallbackImpl;
import com.y3tu.tool.core.pojo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author y3tu
 * @date 2019-05-09
 */
@FeignClient(name = ServiceNameConstants.AUTHENTICATION_SERVER, fallback = AuthenticationServiceFallbackImpl.class)
public interface AuthenticationService {

    @RequestMapping(method = RequestMethod.POST, value = "/auth/decide")
    public boolean decide(@RequestParam String url, @RequestParam String method, HttpServletRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/auth/hasPermission")
    public boolean hasPermission(HttpServletRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/auth/ignoreAuthentication")
    public boolean ignoreAuthentication(@RequestParam String url);
}
