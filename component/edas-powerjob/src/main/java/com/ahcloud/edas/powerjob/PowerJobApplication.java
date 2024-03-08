package com.ahcloud.edas.powerjob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 15:46
 **/
@SpringBootApplication(scanBasePackages = {"com.ahcloud.edas"})
@MapperScan(basePackages = {"com.ahcloud.edas.powerjob.biz.infrastructure.repository.mapper"} )

public class PowerJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(PowerJobApplication.class, args);
    }
}
