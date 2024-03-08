package com.ahcloud.edas.pulsar.core.domain.subscription.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/20 10:47
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SubscriptionQuery extends PageQuery {

    /**
     * topicId
     */
    private Long topicId;

    /**
     * 订阅名称
     */
    private String name;
}
