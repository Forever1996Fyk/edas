package com.ahcloud.edas.nacos;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 00:46
 **/
@SpringBootApplication(scanBasePackages = {"com.ahcloud.edas"})
@MapperScan(basePackages = {"com.ahcloud.edas.nacos.biz.infrastructure.repository.mapper"} )
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }
}
