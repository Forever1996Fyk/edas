package com.ahcloud.edas.admin.biz.application.service.ext;

import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenuApi;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-26 19:04
 **/
public interface SysMenuApiExtService extends IService<SysMenuApi> {

    /**
     * 真删除
     * @param wrapper
     * @return
     */
    int delete(Wrapper<SysMenuApi> wrapper);
}

