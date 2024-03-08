package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysUserRoleExtService;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserRole;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-01-04
 */
@Service
public class SysUserRoleExtServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleExtService {

    @Override
    public int delete(Wrapper<SysUserRole> queryWrapper) {
        return baseMapper.delete(queryWrapper);
    }
}
