package com.y3tu.cloud.gateway;

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
@ComponentScan({"com.y3tu.cloud.common", "com.y3tu.cloud.gateway"})
@EnableZuulProxy
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableDefaultExceptionAdvice
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
