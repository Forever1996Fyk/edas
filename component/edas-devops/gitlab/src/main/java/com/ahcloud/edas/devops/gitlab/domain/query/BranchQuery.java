package com.ahcloud.edas.devops.gitlab.domain.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 10:12
 **/
@Data
public class BranchQuery extends PageQuery {

    /**
     * 分支名称
     */
    private String name;

    /**
     * 项目id
     */
    private Integer projectId;
}
