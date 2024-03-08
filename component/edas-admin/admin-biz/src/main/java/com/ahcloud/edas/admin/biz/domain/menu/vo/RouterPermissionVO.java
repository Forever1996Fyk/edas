package com.ahcloud.edas.admin.biz.domain.menu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/31 16:08
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RouterPermissionVO {

    /**
     * 路由列表
     */
    private List<RouterVo> routerList;

    /**
     * 按钮权限
     */
    private List<String> buttonPermissionList;
}
