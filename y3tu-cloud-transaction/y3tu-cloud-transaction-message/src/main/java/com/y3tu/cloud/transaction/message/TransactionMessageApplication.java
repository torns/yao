package com.y3tu.cloud.transaction.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
public class TransactionMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionMessageApplication.class, args);
    }

}
