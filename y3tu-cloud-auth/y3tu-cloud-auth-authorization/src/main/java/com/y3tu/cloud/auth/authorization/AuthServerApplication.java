package com.y3tu.cloud.auth.authorization;

import com.y3tu.cloud.auth.authorization.annotation.EnableAuthJwtTokenStore;
import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdivce;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
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
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@EnableDefaultExceptionAdivce
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
