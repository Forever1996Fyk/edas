package com.ahcloud.edas.admin.biz.application.service.ext.impl;

import com.ahcloud.edas.admin.biz.application.service.ext.SysDeptExtService;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDept;
import com.ahcloud.edas.admin.biz.infrastructure.repository.mapper.SysDeptMapper;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 17:08
 **/
@Service
public class SysDeptExtServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptExtService {

    @Override
    public SysDept getOneByCode(String deptCode) {
        return baseMapper.selectOne(
                new QueryWrapper<SysDept>().lambda()
                        .eq(SysDept::getDeptCode, deptCode)
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
    }
}
