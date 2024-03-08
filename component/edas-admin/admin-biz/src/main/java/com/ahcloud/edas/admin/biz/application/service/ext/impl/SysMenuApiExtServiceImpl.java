package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysMenuApiExtService;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenuApi;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysMenuApiMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-26 19:05
 **/
@Service
public class SysMenuApiExtServiceImpl extends ServiceImpl<SysMenuApiMapper, SysMenuApi> implements SysMenuApiExtService {

    @Override
    public int delete(Wrapper<SysMenuApi> wrapper) {
        return baseMapper.delete(wrapper);
    }
}
