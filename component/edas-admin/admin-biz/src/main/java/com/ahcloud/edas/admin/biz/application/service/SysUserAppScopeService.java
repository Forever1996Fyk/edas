package com.ahcloud.edas.admin.biz.application.service;

import com.ahcloud.edas.admin.biz.domain.app.query.AppBaseQuery;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysAppPark;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserApi;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserAppScope;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户app作用域 服务类
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-04
 */
public interface SysUserAppScopeService extends IService<SysUserAppScope> {

    /**
     * 查询app信息
     * @param query
     * @return
     */
    List<SysAppPark> listByQuery(AppBaseQuery query);


    /**
     * 删除用户app权限
     * @param queryWrapper
     * @return
     */
    int delete(Wrapper<SysUserAppScope> queryWrapper);
}
