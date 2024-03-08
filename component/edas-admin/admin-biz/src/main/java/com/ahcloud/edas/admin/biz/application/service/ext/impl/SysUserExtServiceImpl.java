package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysUserExtService;
import com.ahcloud.edas.admin.biz.domain.user.query.SysUserExportQuery;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUser;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysUserMapper;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.ext.SysUserExtMapper;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-11 10:51
 **/
@Service
public class SysUserExtServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserExtService {
    @Resource
    private SysUserExtMapper sysUserExtMapper;

    @Override
    public SysUser getOneByUserId(Long userId) {
        return baseMapper.selectOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getUserId, userId)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
    }

    @Override
    public Set<SysUser> listByUserId(Collection<Long> userIds) {
        List<SysUser> sysUsers = baseMapper.selectList(
                new QueryWrapper<SysUser>().lambda()
                        .in(SysUser::getUserId, userIds)
                        .eq(SysUser::getStatus, UserStatusEnum.NORMAL.getStatus())
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        return Sets.newHashSet(sysUsers);
    }

    @Override
    public void streamQueryForExport(SysUserExportQuery query, ResultHandler<SysUser> resultHandler) {
        sysUserExtMapper.streamQueryForExport(query, resultHandler);
    }
}
