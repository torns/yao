package com.y3tu.cloud.auth.authorization;

import com.y3tu.cloud.auth.authorization.annotation.EnableAuthJwtTokenStore;
import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 授权服务
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableDefaultExceptionAdvice
@EnableAuthJwtTokenStore
@ComponentScan(basePackages = {"com.y3tu.cloud.auth.authorization", "com.y3tu.cloud.common"})
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
