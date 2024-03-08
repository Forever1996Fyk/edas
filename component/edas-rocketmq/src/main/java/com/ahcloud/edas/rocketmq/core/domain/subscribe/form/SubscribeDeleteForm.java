package com.ahcloud.edas.rocketmq.core.domain.subscribe.form;

import lombok.Data;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/10 16:43
 **/
@Data
public class SubscribeDeleteForm {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 删除broker列表
     */
    private List<String> brokerNameList;
}
