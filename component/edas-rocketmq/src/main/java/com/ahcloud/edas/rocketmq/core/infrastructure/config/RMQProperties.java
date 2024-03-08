package com.ahcloud.edas.rocketmq.core.infrastructure.config;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.MixAll;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

import static org.apache.rocketmq.client.ClientConfig.SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 09:27
 **/
@Data
@ConfigurationProperties(prefix = "rocketmq.config")
public class RMQProperties {

    /**
     * 集群名称
     */
    private List<String> clusterNameList;

    /**
     * nameserver addr
     */
    private volatile String namesrvAddr = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY, System.getenv(MixAll.NAMESRV_ADDR_ENV));

    /**
     * 是否开启vip channel
     */
    private volatile String isVIPChannel = System.getProperty(SEND_MESSAGE_WITH_VIP_CHANNEL_PROPERTY, "true");

    /**
     * 是否开启控制台收集
     */
    private boolean enableDashBoardCollect;

    /**
     * 是否使用TLS
     */
    private boolean useTLS = false;

    /**
     * 超时时长
     */
    private Long timeoutMillis;

    /**
     * 账号
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * nameserver addr list
     */
    private List<String> namesrvAddrs = new ArrayList<>();

    public void setNamesrvAddrs(List<String> namesrvAddrs) {
        this.namesrvAddrs = namesrvAddrs;
        if (CollectionUtils.isNotEmpty(namesrvAddrs)) {
            this.setNamesrvAddr(namesrvAddrs.get(0));
        }
    }

    public void setNamesrvAddr(String namesrvAddr) {
        if (StringUtils.isNotBlank(namesrvAddr)) {
            this.namesrvAddr = namesrvAddr;
            System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, namesrvAddr);
        }
    }
}
