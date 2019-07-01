package com.y3tu.yao.back;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import com.y3tu.tool.web.annotation.EnableToolWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 后台管理
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDefaultExceptionAdvice
@EnableToolWeb
@ComponentScan(basePackages="com.y3tu.yao")
public class BackServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackServerApplication.class, args);
    }
}
