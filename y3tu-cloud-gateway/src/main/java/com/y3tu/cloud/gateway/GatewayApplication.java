package com.y3tu.cloud.gateway;

import com.y3tu.tool.cache.annotation.EnableToolRedis;
import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 网关
 *
 * @author y3tu
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableDefaultExceptionAdvice
@EnableToolRedis
@ComponentScan(basePackages = {"com.y3tu.cloud.gateway", "com.y3tu.cloud.common"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
