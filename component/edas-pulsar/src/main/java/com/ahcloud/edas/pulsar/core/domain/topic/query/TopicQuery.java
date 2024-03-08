package com.ahcloud.edas.pulsar.core.domain.topic.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 16:05
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TopicQuery extends PageQuery {

    /**
     * topic名称
     */
    private String topicName;

    /**
     * 环境变量
     */
    private String env;
}
