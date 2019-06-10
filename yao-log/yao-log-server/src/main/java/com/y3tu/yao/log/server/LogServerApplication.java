package com.y3tu.yao.log.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 日志服务
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogServerApplication.class, args);
    }

}
