package com.ahcloud.edas.devops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 17:10
 **/
@SpringBootApplication(scanBasePackages = {"com.ahcloud.edas"})
@MapperScan(basePackages = {"com.ahcloud.edas.devops.jenkins.biz.infrastructure.repository.mapper", "com.ahcloud.edas.devops.config.biz.infrastructure.repository.mapper"} )
public class DevopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevopsApplication.class, args);
    }
}
