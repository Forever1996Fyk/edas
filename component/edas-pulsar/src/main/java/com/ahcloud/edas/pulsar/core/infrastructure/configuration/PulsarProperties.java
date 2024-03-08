package com.ahcloud.edas.pulsar.core.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/18 15:50
 **/
@Data
@ConfigurationProperties(prefix = "edas.pulsar")
public class PulsarProperties {

    /**
     * 服务地址
     */
    private String serviceHttpUrl;

    /**
     * 身份认证
     */
    private String token;

    /**
     * 是否在群集中强制执行 TLS 验证。
     */
    private boolean useKeyStoreTls;

    /**
     * 从客户端接受不受信任的 TLS 证书。
     */
    private boolean allowTlsInsecureConnection;

    /**
     * 是否启动hostname认证
     */
    private boolean enableTlsHostnameVerification;
}
