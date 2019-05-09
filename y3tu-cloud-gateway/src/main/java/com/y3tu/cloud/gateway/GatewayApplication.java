package com.y3tu.cloud.gateway;

import com.y3tu.cloud.common.config.FilterIgnorePropertiesConfig;
import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * 网关
 *
 * @author y3tu
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableDefaultExceptionAdvice
@Import(FilterIgnorePropertiesConfig.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
