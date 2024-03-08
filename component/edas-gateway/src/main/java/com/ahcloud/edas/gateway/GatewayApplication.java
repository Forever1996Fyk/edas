package com.ahcloud.edas.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 11:11
 **/
@SpringBootApplication(scanBasePackages = {"com.ahcloud.edas"})
@MapperScan(basePackages = {"com.ahcloud.edas.gateway.core.infrastructure.repository"})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
