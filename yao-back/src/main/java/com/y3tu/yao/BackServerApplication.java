package com.y3tu.yao;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import com.y3tu.tool.web.annotation.EnableToolWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 后台管理
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDefaultExceptionAdvice
@EnableToolWeb
public class BackServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackServerApplication.class, args);
    }
}
