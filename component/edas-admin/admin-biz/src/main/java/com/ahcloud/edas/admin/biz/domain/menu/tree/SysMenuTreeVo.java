package com.ahcloud.edas.admin.biz.domain.menu.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-22 15:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuTreeVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型
     */
    private Integer menuType;

    /**
     * 菜单序号
     */
    private Integer menuOrder;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单url
     */
    private String menuPath;

    /**
     * 打开方式(1:页面内,2:外链)
     */
    private Integer openType;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 是否隐藏
     */
    private Integer hidden;

    /**
     * 子菜单
     */
    private List<SysMenuTreeVo> children;

    /**
     * 菜单类型名称
     */
    private String menuTypeName;

    /**
     * 打开类型名称
     */
    private String openTypeName;


    /**
     * 激活页面路径
     */
    private String activeMenu;

    /**
     * 动态创建新的tab
     */
    private Integer dynamicNewTab;
}
