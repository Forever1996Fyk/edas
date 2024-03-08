package com.ahcloud.edas.powerjob.biz.domain.job.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:45
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class JobQuery extends PageQuery {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * appId
     */
    private Long appId;
}
