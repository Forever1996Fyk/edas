package com.ahcloud.edas.rocketmq.core.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/15 09:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageProperties {

    /**
     * tag
     */
    private String tag;

    /**
     * key
     */
    private String key;
}
