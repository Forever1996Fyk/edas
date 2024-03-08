package com.ahcloud.edas.admin.biz.application.checker;

import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuAddForm;
import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuUpdateForm;
import com.ahcloud.edas.admin.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.MenuOpenTypeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.MenuQueryTypeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.MenuTypeEnum;
import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-30 11:29
 **/
@Component
public class SysMenuChecker {

    public void checkSysMenuQueryType(SysMenuTreeSelectQuery query) {
        if (query.getSysMenuQueryType() == null) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "菜单树查询类型");
        }

        if (MenuQueryTypeEnum.getByType(query.getSysMenuQueryType()).equals(MenuQueryTypeEnum.ROLE_MENU)
                && StringUtils.isBlank(query.getRoleCode())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "角色编码");
        }

    }

    public void checkAndAssign(SysMenuAddForm form) {
        if (MenuTypeEnum.isNotButton(form.getMenuType())) {
            if (StringUtils.isBlank(form.getMenuPath())) {
                throw new BizException(ErrorCodeEnum.PARAM_MISS, "菜单路径");
            }
            if (StringUtils.isBlank(form.getMenuIcon())) {
                throw new BizException(ErrorCodeEnum.PARAM_MISS, "菜单图标");
            }
        } else {
            form.setDynamicNewTab(YesOrNoEnum.NO.getType());
            form.setOpenType(MenuOpenTypeEnum.FRAME_TARGET.getType());
            form.setHidden(YesOrNoEnum.YES.getType());
        }
    }

    public void checkAndAssign(SysMenuUpdateForm form) {
        if (MenuTypeEnum.isNotButton(form.getMenuType())) {
            if (StringUtils.isBlank(form.getMenuPath())) {
                throw new BizException(ErrorCodeEnum.PARAM_MISS, "菜单路径");
            }
            if (StringUtils.isBlank(form.getMenuIcon())) {
                throw new BizException(ErrorCodeEnum.PARAM_MISS, "菜单图标");
            }
        } else {
            form.setDynamicNewTab(YesOrNoEnum.NO.getType());
            form.setOpenType(MenuOpenTypeEnum.FRAME_TARGET.getType());
            form.setHidden(YesOrNoEnum.YES.getType());
        }
    }
}
