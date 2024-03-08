package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuAddForm;
import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuApiAddForm;
import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuUpdateForm;
import com.ahcloud.edas.admin.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ahcloud.edas.admin.biz.domain.menu.tree.SysMenuTreeVo;
import com.ahcloud.edas.admin.biz.domain.menu.vo.RouterVo;
import com.ahcloud.edas.admin.biz.domain.menu.vo.SysMenuVo;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.MenuOpenTypeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenuApi;
import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Component
public class SysMenuHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysMenu convertToEntity(SysMenuAddForm form) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(form);
        sysMenu.setParentId(sysMenu.getParentId() != null ? sysMenu.getParentId() : AdminConstants.ZERO);
        sysMenu.setCreator(UserUtils.getUserNameBySession());
        sysMenu.setModifier(UserUtils.getUserNameBySession());
        return sysMenu;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysMenu convertToEntity(SysMenuUpdateForm form) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.convert(form);
        sysMenu.setModifier(UserUtils.getUserNameBySession());
        return sysMenu;
    }

    /**
     * 数据转换
     * @param sysMenu
     * @return
     */
    public SysMenuVo convertEntityToVo(SysMenu sysMenu) {
        return SysMenuConvert.INSTANCE.convert(sysMenu);
    }

    /**
     * 数据转换菜单路由
     * @param sysMenuList
     * @return
     */
    public List<RouterVo> convertToRouteVo(List<SysMenu> sysMenuList) {
        return convertToRouteTree(sysMenuList, AdminConstants.ZERO);
    }

    private List<RouterVo> convertToRouteTree(List<SysMenu> sysMenuList, Long pid) {
        List<RouterVo> routerVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                RouterVo routerVo = buildRouterVoByMenu(sysMenu);
                List<RouterVo> childRouterVos = convertToRouteTree(sysMenuList, sysMenu.getId());
                routerVo.setChildren(childRouterVos);
                routerVoList.add(routerVo);
            }
        }
        return routerVoList;
    }

    private RouterVo buildRouterVoByMenu(SysMenu sysMenu) {
        return RouterVo.builder()
                .name(sysMenu.getMenuCode())
                .hidden(isHidden(sysMenu))
                .component(sysMenu.getComponent())
                .path(sysMenu.getMenuPath())
                .meta(buildMetaVoByMenu(sysMenu))
                .build();
    }

    private RouterVo.MetaVo buildMetaVoByMenu(SysMenu sysMenu) {
        return RouterVo.MetaVo.builder()
                .icon(sysMenu.getMenuIcon())
                .hidden(YesOrNoEnum.getByType(sysMenu.getHidden()).isYes())
                .title(sysMenu.getMenuName())
                .levelHidden(false)
                .activeMenu(sysMenu.getActiveMenu())
                .dynamicNewTab(YesOrNoEnum.getByType(sysMenu.getDynamicNewTab()).isYes())
                .build();
    }

    /**
     * 菜单是否隐藏
     * @param sysMenu
     * @return
     */
    private boolean isHidden(SysMenu sysMenu) {
        return Objects.equals(sysMenu.getHidden(), YesOrNoEnum.YES.getType());
    }

    /**
     *  判断是否为菜单内部跳转
     * @param sysMenu
     * @return
     */
    private boolean isMenuFrame(SysMenu sysMenu) {
        return Objects.equals(sysMenu.getOpenType(), MenuOpenTypeEnum.FRAME_TARGET.getType());
    }

    /**
     * 数据转换
     *
     * 菜单树选择列表转换
     *
     * @param sysMenuList
     * @param roleMenuIdSet
     * @return
     */
    public List<SysMenuTreeSelectVo> convertToTreeSelectVo(List<SysMenu> sysMenuList, Set<Long> roleMenuIdSet) {
        return convertToTreeSelectVo(sysMenuList, AdminConstants.ZERO, roleMenuIdSet);
    }

    private List<SysMenuTreeSelectVo> convertToTreeSelectVo(List<SysMenu> sysMenuList, Long pid, Set<Long> roleMenuIdSet) {
        List<SysMenuTreeSelectVo> sysMenuTreeSelectVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                SysMenuTreeSelectVo sysMenuTreeSelectVo = buildSysMenuTreeSelectVoByMenu(sysMenu, roleMenuIdSet);
                List<SysMenuTreeSelectVo> childSysMenuTreeSelectVoList = convertToTreeSelectVo(sysMenuList, sysMenu.getId(), roleMenuIdSet);
                sysMenuTreeSelectVo.setChildren(childSysMenuTreeSelectVoList);
                sysMenuTreeSelectVoList.add(sysMenuTreeSelectVo);
            }
        }
        return sysMenuTreeSelectVoList;
    }

    private SysMenuTreeSelectVo buildSysMenuTreeSelectVoByMenu(SysMenu sysMenu,Set<Long> roleMenuIdSet) {
        return SysMenuTreeSelectVo.builder()
                .id(String.valueOf(sysMenu.getId()))
                .label(sysMenu.getMenuName())
                .disabled(false)
                .selected(roleMenuIdSet.contains(sysMenu.getId()))
                .build();
    }

    /**
     * 数据转换
     *
     * 菜单树列表 转换
     * @param sysMenuList
     * @return
     */
    public List<SysMenuTreeVo> convertToTreeVo(List<SysMenu> sysMenuList) {
        return convertToMenuTreeVo(sysMenuList, AdminConstants.ZERO);
    }

    private List<SysMenuTreeVo> convertToMenuTreeVo(List<SysMenu> sysMenuList, Long pid) {
        List<SysMenuTreeVo> routerVoList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (Objects.equals(sysMenu.getParentId(), pid)) {
                SysMenuTreeVo sysMenuTreeVo = buildMenuTreeVoByMenu(sysMenu);
                List<SysMenuTreeVo> childRouterVos = convertToMenuTreeVo(sysMenuList, sysMenu.getId());
                sysMenuTreeVo.setChildren(childRouterVos);
                routerVoList.add(sysMenuTreeVo);
            }
        }
        return routerVoList;
    }

    private SysMenuTreeVo buildMenuTreeVoByMenu(SysMenu sysMenu) {
        return SysMenuConvert.INSTANCE.convertToTree(sysMenu);
    }

    public List<SysMenuApi> getSysMenuApiEntityList(SysMenuApiAddForm form) {
        List<SysMenuApi> sysMenuApiList = Lists.newArrayList();
        String userNameBySession = UserUtils.getUserNameBySession();
        for(String apiCode : form.getApiCodeList()) {
            SysMenuApi sysMenuApi = new SysMenuApi();
            sysMenuApi.setMenuId(form.getMenuId());
            sysMenuApi.setMenuCode(form.getMenuCode());
            sysMenuApi.setApiCode(apiCode);
            sysMenuApi.setCreator(userNameBySession);
            sysMenuApi.setModifier(userNameBySession);
            sysMenuApiList.add(sysMenuApi);
        }
        return sysMenuApiList;
    }

    @Mapper
    public interface SysMenuConvert {
        SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysMenu convert(SysMenuAddForm form);

        /**
         * 数据换砖
         * @param form
         * @return
         */
        SysMenu convert(SysMenuUpdateForm form);

        /**
         * 数据转换
         * @param sysMenu
         * @return
         */
        SysMenuVo convert(SysMenu sysMenu);

        /**
         * 数据转换
         * @param sysMenu
         * @return
         */
        @Mappings({
                @Mapping(target = "menuTypeName", expression = "java(com.ahcloud.edas.admin.biz.infrastructure.constant.enums.MenuTypeEnum.getByType(sysMenu.getMenuType()).getDesc())"),
                @Mapping(target = "openTypeName", expression = "java(com.ahcloud.edas.admin.biz.infrastructure.constant.enums.MenuOpenTypeEnum.getByType(sysMenu.getOpenType()).getDesc())")
        })
        SysMenuTreeVo convertToTree(SysMenu sysMenu);
    }
}
