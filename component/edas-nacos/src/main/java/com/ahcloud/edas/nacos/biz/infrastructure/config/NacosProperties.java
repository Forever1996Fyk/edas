package com.ahcloud.edas.nacos.biz.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:47
 **/
@Data
@ConfigurationProperties(prefix = "edas.nacos.open-api")
public class NacosProperties {

    /**
     * nacos地址
     */
    private String url;

    /**
     * nacos账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间
     */
    private int conTimeOutMillis;

    /**
     * 读取超时时间
     */
    private int readTimeOutMillis;

}
