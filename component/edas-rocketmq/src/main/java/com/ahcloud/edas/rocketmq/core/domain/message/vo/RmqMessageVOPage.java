package com.ahcloud.edas.rocketmq.core.domain.message.vo;

import com.ahcloud.edas.common.domain.common.PageResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/14 11:33
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmqMessageVOPage {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 分页结果
     */
    private PageResult<MessageVO> pageResult;
}
