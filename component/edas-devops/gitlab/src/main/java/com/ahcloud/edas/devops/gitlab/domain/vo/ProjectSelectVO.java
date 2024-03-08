package com.ahcloud.edas.devops.gitlab.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 10:14
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSelectVO {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目id
     */
    private Integer projectId;
}
