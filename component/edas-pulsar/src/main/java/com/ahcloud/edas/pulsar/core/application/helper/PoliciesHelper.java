package com.ahcloud.edas.pulsar.core.application.helper;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
import org.apache.pulsar.common.policies.data.AutoSubscriptionCreationOverride;
import org.apache.pulsar.common.policies.data.Policies;
import org.apache.pulsar.common.policies.data.RetentionPolicies;
import org.apache.pulsar.common.policies.data.impl.AutoSubscriptionCreationOverrideImpl;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 11:04
 **/
public class PoliciesHelper {

    /**
     * 构建策略
     *
     * @param policyDTO
     * @return
     */
    public static Policies buildPolicies(PolicyDTO policyDTO) {
        Policies policies = new Policies();
        policies.retention_policies = buildRetention(policyDTO);
        policies.message_ttl_in_seconds = parseMessageTtlSeconds(policyDTO);
        policies.autoSubscriptionCreationOverride = buildAutoSubscriptionCreation(policyDTO);
        return policies;
    }

    public static Integer parseMessageTtlSeconds(PolicyDTO policyDTO) {
        Integer unit = policyDTO.getUnit();
        Integer ttl = policyDTO.getNamespaceMessageTTL();
        switch (unit) {
            case 1:
                return ttl;
            case 2:
                return ttl * 60 * 60;
            case 3:
                ttl = ttl > 15 ? 15 : ttl;
                return ttl * 24 * 60 * 60;
            default:
                throw new BizException(PulsarRetCodeEnum.PARAM_ILLEGAL);
        }
    }

    /**
     * 构建保留策略
     *
     * @param policyDTO
     * @return
     */
    public static RetentionPolicies buildRetention(PolicyDTO policyDTO) {
        if (policyDTO.getRetentionPolicies() == 0) {
            return new RetentionPolicies(0, 0);
        } else {
            return new RetentionPolicies(-1, -1);
        }
    }


    /**
     * 构建是否自动创建订阅策略
     *
     * @param policyDTO
     * @return
     */
    public static AutoSubscriptionCreationOverride buildAutoSubscriptionCreation(PolicyDTO policyDTO) {
        return AutoSubscriptionCreationOverrideImpl.builder()
                .allowAutoSubscriptionCreation(policyDTO.getAutoCreateSubscription())
                .build();
    }
}
