package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysRoleApiExtService;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysRoleApi;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysRoleApiMapper;
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
public class SysRoleApiExtServiceImpl extends ServiceImpl<SysRoleApiMapper, SysRoleApi> implements SysRoleApiExtService {

    @Override
    public int delete(Wrapper<SysRoleApi> wrapper) {
        return baseMapper.delete(wrapper);
    }
}
