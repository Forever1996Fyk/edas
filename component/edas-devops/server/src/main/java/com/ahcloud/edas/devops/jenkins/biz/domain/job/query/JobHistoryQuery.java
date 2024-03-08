package com.ahcloud.edas.devops.jenkins.biz.domain.job.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

import java.util.Date;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 16:40
 **/
@Data
public class JobHistoryQuery extends PageQuery {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 应用id
     */
    private Long appId;
}
