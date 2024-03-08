package com.ahcloud.edas.powerjob.biz.domain.instance.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 10:05
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class JobInstanceQuery extends PageQuery {

    /**
     * powerJob appId
     */
    private Long powerJobAppId;

    /**
     * 任务id
     */
    private Long jobId;
}
