package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysUserIntroExtService;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserIntro;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysUserIntroMapper;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 22:13
 **/
@Service
public class SysUserIntroExtServiceImpl extends ServiceImpl<SysUserIntroMapper, SysUserIntro> implements SysUserIntroExtService {

    @Override
    public SysUserIntro getOneByUserId(Long userId) {
        return baseMapper.selectOne(
                new QueryWrapper<SysUserIntro>().lambda()
                        .eq(SysUserIntro::getUserId, userId)
                        .eq(SysUserIntro::getDeleted, DeletedEnum.NO.value)
        );
    }
}
