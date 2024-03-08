package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysUserMenuExtService;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysUserMenuMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统用户角色表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-01-04
 */
@Service
public class SysUserMenuExtServiceImpl extends ServiceImpl<SysUserMenuMapper, SysUserMenu> implements SysUserMenuExtService {

    @Override
    public int delete(Wrapper<SysUserMenu> queryWrapper) {
        return baseMapper.delete(queryWrapper);
    }

    @Override
    public List<SysMenu> listButtonMenuByUserId(Long userId) {
        return baseMapper.listButtonMenuByUserId(userId);
    }
}
