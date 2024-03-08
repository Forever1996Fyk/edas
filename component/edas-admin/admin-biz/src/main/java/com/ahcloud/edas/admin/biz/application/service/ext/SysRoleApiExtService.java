package com.ahcloud.edas.admin.biz.application.service.ext;

import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysRoleApi;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-26 19:03
 **/
public interface SysRoleApiExtService extends IService<SysRoleApi> {

    /**
     * 真删除
     * @param wrapper
     * @return
     */
    int delete(Wrapper<SysRoleApi> wrapper);
}

