package com.y3tu.yao.gateway.filter;

import com.y3tu.yao.common.config.FilterIgnorePropertiesConfig;
import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.gateway.exception.NoPermissionException;
import com.y3tu.yao.gateway.exception.UnAuthorizedException;
import com.y3tu.yao.gateway.feign.AuthenticationService;
import com.y3tu.tool.core.exception.ServerCallException;
import com.y3tu.tool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关权限过滤器
 *
 * @author y3tu
 */
@Component
@Slf4j
public class AccessGatewayFilter implements GlobalFilter {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    /**
     * 1.首先网关检查token是否有效，无效直接返回401，不调用签权服务
     * 2.调用签权服务器看是否对该请求有权限，有权限进入下一个filter，没有权限返回401
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        log.debug("url:{},method:{},headers:{}", url, method, request.getHeaders());
        //不需要网关签权的url
        if (this.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }
        boolean hasPermission = false;
        try {
            //调用签权服务看用户是否有权限，若有权限进入下一个filter
            hasPermission = authenticationService.hasPermission(authentication, url, method);

        } catch (Exception e) {
            String msg = e.getMessage();
            //判断是不是token过期失效
            if (msg.contains("401")) {
                throw new UnAuthorizedException("未授权或token过期，请重新登录！", e);
            }
            String serverName = ServerNameConstants.AUTHENTICATION_SERVER;
            String str = StrUtil.format("{}服务调用{}异常,参数：authentication:{},url{},method{}", serverName, "hasPermission", authentication, url, method);
            throw new ServerCallException(str, e);
        }
        if (hasPermission) {
            ServerHttpRequest.Builder builder = request.mutate();
            //如果每个微服务需要做验证，则转发的请求都加上服务间认证token
            //如果只在网关做验证则不需要
            return chain.filter(exchange.mutate().request(builder.build()).build());
        } else {
            throw new NoPermissionException("当前操作没有权限");
        }
    }


    private boolean ignoreAuthentication(String url) {
        return filterIgnorePropertiesConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StrUtil.trim(ignoreUrl)));
    }
}
