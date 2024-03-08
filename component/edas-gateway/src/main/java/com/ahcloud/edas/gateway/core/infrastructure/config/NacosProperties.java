package com.ahcloud.edas.gateway.core.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:47
 **/
@Data
@ConfigurationProperties(prefix = "edas.gateway.nacos")
public class NacosProperties {

    /**
     * nacos serverAddr
     */
    private String serverAddr;

    /**
     * nacos账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 命名空间
     */
    private Map<String, String> namespaceMap;

    /**
     * gateway-server group
     */
    private String group;

    /**
     * 设置获取配置信息的轮询超时时间
     */
    private int configLongPollTimeout = 3000;

    /**
     * 设置获取配置信息失败后的重试次数
     */
    private int configRetryTime = 5;

    /**
     * 最大重试次数
     */
    private int maxRetry = 5;

}
