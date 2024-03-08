package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.admin;

import com.ahcloud.edas.rocketmq.core.infrastructure.config.RMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.apache.rocketmq.tools.admin.MQAdminExt;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 09:35
 **/
@Slf4j
public class MQAdminFactory {

    private RMQProperties properties;

    public MQAdminFactory(RMQProperties properties) {
        this.properties = properties;
    }

    private final AtomicLong adminIndex = new AtomicLong(0);

    /**
     * 获取 MQ admin 控制类实例
     * @return
     * @throws Exception
     */
    public MQAdminExt getInstance() throws Exception {
        RPCHook rpcHook = null;
        final String accessKey = properties.getAccessKey();
        final String secretKey = properties.getSecretKey();
        boolean isEnableAcl = StringUtils.isNotEmpty(accessKey) && StringUtils.isNotEmpty(secretKey);
        if (isEnableAcl) {
            rpcHook = new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
        }
        DefaultMQAdminExt mqAdminExt = null;
        if (properties.getTimeoutMillis() == null) {
            mqAdminExt = new DefaultMQAdminExt(rpcHook);
        } else {
            mqAdminExt = new DefaultMQAdminExt(rpcHook, properties.getTimeoutMillis());
        }
        mqAdminExt.setAdminExtGroup(mqAdminExt.getAdminExtGroup() + "_" + adminIndex.getAndIncrement());
        mqAdminExt.setVipChannelEnabled(Boolean.parseBoolean(properties.getIsVIPChannel()));
        mqAdminExt.setUseTLS(properties.isUseTLS());
        mqAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        mqAdminExt.start();
        log.info("create MQAdmin instance {} success.", mqAdminExt);
        return mqAdminExt;
    }
}
