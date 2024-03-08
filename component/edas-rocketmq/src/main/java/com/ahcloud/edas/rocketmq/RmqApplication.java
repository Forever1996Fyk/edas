package com.ahcloud.edas.rocketmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/10 09:48
 **/
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication(scanBasePackages = {"com.ahcloud.edas"})
@MapperScan(basePackages = {"com.ahcloud.edas.rocketmq.core.infrastructure.repository.mapper"} )
public class RmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmqApplication.class, args);
    }
}
