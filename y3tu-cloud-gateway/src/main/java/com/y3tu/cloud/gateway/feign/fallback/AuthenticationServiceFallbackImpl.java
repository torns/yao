package com.y3tu.cloud.gateway.feign.fallback;

import com.y3tu.cloud.gateway.feign.AuthenticationService;
import com.y3tu.tool.core.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * @author y3tu
 */
@Slf4j
@Service
public class AuthenticationServiceFallbackImpl implements AuthenticationService {


    @Override
    public boolean decide(String url, String method, HttpServletRequest request) {
        log.error("调用{}异常{}", "decide", request);
        return false;
    }

    @Override
    public boolean hasPermission(HttpServletRequest request) {
        log.error("调用{}异常{}", "hasPermission", request);
        return false;
    }

    @Override
    public boolean ignoreAuthentication(String url) {
        log.error("调用{}异常{}", "ignoreAuthentication", url);
        return false;
    }
}
