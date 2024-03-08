package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.aspect;

import com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.client.MQAdminInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 09:43
 **/
@Slf4j
@Aspect
@Service
public class MQAdminAspect {

    @Resource
    private GenericObjectPool<MQAdminExt> mqAdminExtPool;

    @Pointcut("execution(* com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service.client.MQAdminExtImpl..*(..))")
    public void mqAdminMethodPointCut() {}

    @Around(value = "mqAdminMethodPointCut()")
    public Object aroundMQAdminMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = null;
        try {
            MQAdminInstance.createMQAdmin(mqAdminExtPool);
            obj = joinPoint.proceed();
        } finally {
            MQAdminInstance.returnMQAdmin(mqAdminExtPool);
            log.debug("op=look method={} cost={}", joinPoint.getSignature().getName(), System.currentTimeMillis() - start);
        }
        return obj;
    }
}
