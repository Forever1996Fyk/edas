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
public class ProjectQuery extends PageQuery {

    /**
     * 项目名称
     */
    private String name;
}
