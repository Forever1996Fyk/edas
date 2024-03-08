package com.ahcloud.edas.admin.biz.application.service.impl;

import com.ahcloud.edas.admin.biz.domain.app.query.AppBaseQuery;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysAppPark;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserAppScope;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysUserAppScopeMapper;
import com.ahcloud.edas.admin.biz.application.service.SysUserAppScopeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户app作用域 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-04
 */
@Service
public class SysUserAppScopeServiceImpl extends ServiceImpl<SysUserAppScopeMapper, SysUserAppScope> implements SysUserAppScopeService {

    @Override
    public List<SysAppPark> listByQuery(AppBaseQuery query) {
        return baseMapper.selectByQuery(query);
    }

    @Override
    public int delete(Wrapper<SysUserAppScope> queryWrapper) {
        return baseMapper.delete(queryWrapper);
    }
}
