package com.ahcloud.edas.admin.biz.domain.menu.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-22 13:59
 **/
@Data
public class SysMenuUpdateForm {

    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 父菜单id
     */
    @NotNull(message = "父id不能为空")
    private Long parentId;

    /**
     * 菜单编码
     */
    @NotEmpty(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * 菜单名称
     */
    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单类型
     */
    @NotNull(message = "菜单类型不能为空")
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
     * 激活页面路径
     */
    private String activeMenu;

    /**
     * 动态创建新的tab
     */
    private Integer dynamicNewTab;

    /**
     * 是否隐藏
     */
    private Integer hidden;
}
