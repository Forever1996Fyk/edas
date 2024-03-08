package com.ahcloud.edas.rocketmq.core.domain.message.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 17:40
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeResult {

    /**
     * 是否顺序
     */
    private Boolean order;

    /**
     * 是否自动提交
     */
    private Boolean autoCommit;

    /**
     * 消费结果
     */
    private Integer consumeResult;

    /**
     * 消费结果
     */
    private String consumeResultName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 花费时长
     */
    private Long spentTimeMills;
}
