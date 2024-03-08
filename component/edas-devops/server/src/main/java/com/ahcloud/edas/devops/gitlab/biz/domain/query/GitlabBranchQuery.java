package com.ahcloud.edas.devops.gitlab.biz.domain.query;

import com.ahcloud.edas.common.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 10:49
 **/
@Data
public class GitlabBranchQuery extends PageQuery {

    /**
     * appId
     */
    private Long appId;

    /**
     *
     */
    private String name;
}
