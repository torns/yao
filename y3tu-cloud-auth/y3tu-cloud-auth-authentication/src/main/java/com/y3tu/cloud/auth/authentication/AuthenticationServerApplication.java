package com.y3tu.cloud.auth.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 签权服务
 *
 * @author y3tu
 * @date 2019-05-09
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServerApplication.class, args);
    }
}
