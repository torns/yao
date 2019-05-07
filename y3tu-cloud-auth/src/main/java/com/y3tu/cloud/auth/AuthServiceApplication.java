package com.y3tu.cloud.auth;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 权限服务
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableDefaultExceptionAdvice
@ComponentScan(basePackages = {"com.y3tu.cloud.auth", "com.y3tu.cloud.common"})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
