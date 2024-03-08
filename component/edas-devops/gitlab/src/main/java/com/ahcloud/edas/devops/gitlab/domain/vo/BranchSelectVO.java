package com.ahcloud.edas.devops.gitlab.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class BranchSelectVO {

    /**
     * 分支名称
     */
    private String name;

    /**
     * 是否保护分支
     */
    private Boolean protectedBranch;
}
