package com.y3tu.cloud.gateway;

import com.y3tu.tool.web.annotation.EnableDefaultExceptionAdivce;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }

}
