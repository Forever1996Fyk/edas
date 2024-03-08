package com.ahcloud.edas.admin.biz.application.service.ext;

import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserMenu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-21 22:25
 **/
public interface SysUserMenuExtService extends IService<SysUserMenu> {

    /**
     * 真-删除数据
     * @param queryWrapper
     * @return
     */
    int delete(Wrapper<SysUserMenu> queryWrapper);

    /**
     * 查询用户的按钮集合
     * @param userId
     * @return
     */
    List<SysMenu> listButtonMenuByUserId(Long userId);
}
