package com.y3tu.yao.authentication;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 签权服务
 *
 * @author y3tu
 * @date 2019-05-09
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableDefaultExceptionAdvice
public class AuthenticationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServerApplication.class, args);
    }
}
