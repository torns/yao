package com.y3tu.cloud.gateway.feign.fallback;

import com.y3tu.cloud.gateway.feign.AuthenticationService;
import com.y3tu.tool.core.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author y3tu
 */
@Slf4j
@Service
public class AuthenticationServiceFallbackImpl implements AuthenticationService {
    @Override
    public R hasPermission(String authentication, String url, String method) {
        log.error("调用{}异常{}", "auth", url);
        return R.error();
    }
}
