package com.y3tu.cloud.zipkin.elk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZipkinElkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinElkApplication.class, args);
    }
}
