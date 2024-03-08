package com.ahcloud.edas.powerjob.biz.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 15:51
 **/
@Data
@ConfigurationProperties(prefix = "powerjob")
public class PowerJobProperties {

    /**
     * powerjob server地址
     */
    private List<String> urlList;

    /**
     * 应用密码
     */
    private String password;
}
