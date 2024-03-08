package com.ahcloud.edas.pulsar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/4 10:59
 **/
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.ahcloud.edas"})
@MapperScan(basePackages = {"com.ahcloud.edas.pulsar.core.infrastructure.repository.mapper"} )
public class PulsarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulsarApplication.class, args);
    }
}
