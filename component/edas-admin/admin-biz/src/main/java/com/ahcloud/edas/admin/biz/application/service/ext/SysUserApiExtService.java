package com.ahcloud.edas.admin.biz.application.service.ext;

import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserApi;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-21 22:25
 **/
public interface SysUserApiExtService extends IService<SysUserApi> {

    /**
     * 真-删除数据
     * @param queryWrapper
     * @return
     */
    int delete(Wrapper<SysUserApi> queryWrapper);
}
