package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.rocketmq.client.impl.MQClientAPIImpl;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.remoting.RemotingClient;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExtImpl;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.joor.Reflect;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 09:49
 **/
@Slf4j
public class MQAdminInstance {

    private static final ThreadLocal<MQAdminExt> MQ_ADMIN_EXT_THREAD_LOCAL = new ThreadLocal<>();

    public static MQAdminExt threadLocalMQAdminExt() {
        MQAdminExt mqAdminExt = MQ_ADMIN_EXT_THREAD_LOCAL.get();
        if (mqAdminExt == null) {
            throw new IllegalStateException("defaultMQAdminExt should be init before you get this");
        }
        return mqAdminExt;
    }

    public static RemotingClient threadLocalRemotingClient() {
        MQClientInstance mqClientInstance = threadLocalMqClientInstance();
        MQClientAPIImpl mQClientAPIImpl = Reflect.on(mqClientInstance).get("mQClientAPIImpl");
        return Reflect.on(mQClientAPIImpl).get("remotingClient");
    }

    public static MQClientInstance threadLocalMqClientInstance() {
        DefaultMQAdminExtImpl defaultMQAdminExtImpl = Reflect.on(MQAdminInstance.threadLocalMQAdminExt()).get("defaultMQAdminExtImpl");
        return Reflect.on(defaultMQAdminExtImpl).get("mqClientInstance");
    }

    public static void createMQAdmin(GenericObjectPool<MQAdminExt> mqAdminExtPool) {
        try {
            // Get the mqAdmin instance from the object pool
            MQAdminExt mqAdminExt = mqAdminExtPool.borrowObject();
            MQ_ADMIN_EXT_THREAD_LOCAL.set(mqAdminExt);
        } catch (Exception e) {
            log.error("get mqAdmin from pool error", e);
        }
    }

    public static void returnMQAdmin(GenericObjectPool<MQAdminExt> mqAdminExtPool) {
        MQAdminExt mqAdminExt = MQ_ADMIN_EXT_THREAD_LOCAL.get();
        if (mqAdminExt != null) {
            try {
                // After execution, return the mqAdmin instance to the object pool
                mqAdminExtPool.returnObject(mqAdminExt);
            } catch (Exception e) {
                log.error("return mqAdmin to pool error", e);
            }
        }
        MQ_ADMIN_EXT_THREAD_LOCAL.remove();
    }
}
