package com.y3tu.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author y3tu
 * 动态路由配置类
 */
@Configuration
public class DynamicRouteConfiguration {
    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public DynamicRouteLocator dynamicRouteLocator() {
        return new DynamicRouteLocator(server.getServlet().getContextPath(), zuulProperties, jdbcTemplate);
    }
}
