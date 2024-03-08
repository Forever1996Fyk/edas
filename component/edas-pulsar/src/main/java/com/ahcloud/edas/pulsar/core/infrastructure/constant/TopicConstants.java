package com.ahcloud.edas.pulsar.core.infrastructure.constant;

import com.google.common.collect.Sets;
import org.apache.pulsar.common.policies.data.AuthAction;

import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 17:02
 **/
public class TopicConstants {

    /**
     * 生产与消费权限
     */
    public final static Set<AuthAction> PRODUCE_CONSUME_AUTH_ACTIONS = Sets.newHashSet(AuthAction.consume, AuthAction.produce);
}
