package com.y3tu.cloud.upms;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import com.y3tu.tool.web.annotation.EnableToolWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 通用用户权限系统
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDefaultExceptionAdvice
@EnableToolWeb
public class UpmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsServiceApplication.class, args);
    }
}
