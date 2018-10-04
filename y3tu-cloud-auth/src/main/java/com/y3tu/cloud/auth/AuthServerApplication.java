package com.y3tu.cloud.auth;

import com.y3tu.cloud.auth.annotation.EnableAuthJwtTokenStore;
import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdivce;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 权限服务
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthJwtTokenStore
@EnableFeignClients
@EnableDefaultExceptionAdivce
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
