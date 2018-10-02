package com.y3tu.cloud.gateway.web;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdivce;
import com.y3tu.tool.web.annotation.EnableFlexDefaultExceptionAdvice;
import com.y3tu.tool.web.exception.DefaultExceptionAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;

/**
 * 网关
 *
 * @author y3tu
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableDefaultExceptionAdivce
public class GatewayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class, args);
    }

    @Bean
    ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }

}
