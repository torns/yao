package com.y3tu.cloud.gateway.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author y3tu
 * 动态路由配置类
 */
@Configuration
public class DynamicRouteConfiguration {
    private Registration registration;
    private DiscoveryClient discovery;
    private ZuulProperties zuulProperties;
    private ServerProperties server;
    private RedisTemplate redisTemplate;

    public DynamicRouteConfiguration(Registration registration, DiscoveryClient discovery,
                                     ZuulProperties zuulProperties, ServerProperties server, RedisTemplate redisTemplate) {
        this.registration = registration;
        this.discovery = discovery;
        this.zuulProperties = zuulProperties;
        this.server = server;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public DiscoveryClientRouteLocator dynamicRouteLocator() {
        return new DynamicRouteLocator(
                server.getServlet().getContextPath()
                , discovery
                , zuulProperties
                , registration
                , redisTemplate);
    }
}
