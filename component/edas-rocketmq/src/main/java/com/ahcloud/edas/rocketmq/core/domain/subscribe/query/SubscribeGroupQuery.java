package com.ahcloud.edas.rocketmq.core.domain.subscribe.query;

import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/11 11:36
 **/
@Data
public class SubscribeGroupQuery {

    /**
     * topic Id
     */
    private Long topicId;

    /**
     * 订阅组名称
     */
    private String groupName;
}
