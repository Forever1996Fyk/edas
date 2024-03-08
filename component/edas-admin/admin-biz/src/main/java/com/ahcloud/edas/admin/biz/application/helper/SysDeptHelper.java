package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.admin.biz.domain.dept.form.DeptAddForm;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptTreeSelectVo;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptTreeVo;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptVo;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDept;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDeptRelation;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 17:06
 **/
@Component
public class SysDeptHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDept convert(DeptAddForm form) {
        SysDept sysDept = Convert.INSTANCE.convert(form);
        sysDept.setCreator(UserUtils.getUserNameBySession());
        sysDept.setModifier(UserUtils.getUserNameBySession());
        return sysDept;
    }


    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDeptRelation convertToRelation(DeptAddForm form) {
        SysDeptRelation sysDeptRelation = new SysDeptRelation();
        sysDeptRelation.setAncestorCode(form.getDeptCode());
        sysDeptRelation.setDescendantCode(StringUtils.equals(form.getParentDeptCode(), AdminConstants.ZERO_STR) ? form.getDeptCode() : form.getParentDeptCode());
        sysDeptRelation.setCreator(UserUtils.getUserNameBySession());
        sysDeptRelation.setModifier(UserUtils.getUserNameBySession());
        return sysDeptRelation;
    }

    /**
     * 数据转换
     * @param sysDept
     * @return
     */
    public SysDeptVo convertToVo(SysDept sysDept) {
        return Convert.INSTANCE.convertToVo(sysDept);
    }

    /**
     * 数据转换
     * @param sysDeptList
     * @return
     */
    public List<SysDeptTreeVo> convertToTree(List<SysDept> sysDeptList) {
        return convertToTree(sysDeptList, AdminConstants.ZERO);
    }

    private List<SysDeptTreeVo> convertToTree(List<SysDept> sysDeptList, Long pid) {
        List<SysDeptTreeVo> sysDeptVoList = Lists.newArrayList();
        for (SysDept sysDept : sysDeptList) {
            if (Objects.equals(sysDept.getParentId(), pid)) {
                SysDeptTreeVo sysDeptVo = Convert.INSTANCE.convertToTreeVo(sysDept);
                List<SysDeptTreeVo> childRouterVos = convertToTree(sysDeptList, sysDept.getId());
                sysDeptVo.setChildren(childRouterVos);
                sysDeptVoList.add(sysDeptVo);
            }
        }
        return sysDeptVoList;
    }

    /**
     * 数据转换
     * @param sysDeptList
     * @return
     */
    public List<SysDeptTreeSelectVo> convertToTreeSelectVo(List<SysDept> sysDeptList) {
        return convertToTreeSelectVo(sysDeptList, AdminConstants.ZERO);
    }

    private List<SysDeptTreeSelectVo> convertToTreeSelectVo(List<SysDept> sysDeptList, Long pid) {
        List<SysDeptTreeSelectVo> sysDeptTreeSelectVoList = Lists.newArrayList();
        for (SysDept sysDept : sysDeptList) {
            if (Objects.equals(sysDept.getParentId(), pid)) {
                SysDeptTreeSelectVo sysDeptTreeSelectVo = buildSysDeptTreeSelectVoByDept(sysDept);
                List<SysDeptTreeSelectVo> childSysMenuTreeSelectVoList = convertToTreeSelectVo(sysDeptList, sysDept.getId());
                sysDeptTreeSelectVo.setChildren(childSysMenuTreeSelectVoList);
                sysDeptTreeSelectVoList.add(sysDeptTreeSelectVo);
            }
        }
        return sysDeptTreeSelectVoList;
    }

    private SysDeptTreeSelectVo buildSysDeptTreeSelectVoByDept(SysDept sysDept) {
        return SysDeptTreeSelectVo.builder()
                .id(String.valueOf(sysDept.getId()))
                .label(sysDept.getName())
                .code(sysDept.getDeptCode())
                .build();
    }


    @Mapper
    public interface Convert {
        Convert INSTANCE = Mappers.getMapper(Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDept convert(DeptAddForm form);

        /**
         * 数据转换
         * @param sysDept
         * @return
         */
        SysDeptVo convertToVo(SysDept sysDept);

        /**
         * 数据转换
         * @param sysDept
         * @return
         */
        SysDeptTreeVo convertToTreeVo(SysDept sysDept);
    }
}
