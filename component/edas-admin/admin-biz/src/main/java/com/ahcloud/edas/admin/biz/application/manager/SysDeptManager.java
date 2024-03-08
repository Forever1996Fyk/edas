package com.ahcloud.edas.admin.biz.application.manager;

import com.ahcloud.edas.admin.biz.application.helper.SysDeptHelper;
import com.ahcloud.edas.admin.biz.application.service.SysDeptRelationService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysDeptExtService;
import com.ahcloud.edas.admin.biz.domain.dept.form.DeptAddForm;
import com.ahcloud.edas.admin.biz.domain.dept.form.DeptUpdateForm;
import com.ahcloud.edas.admin.biz.domain.dept.query.SysDeptQuery;
import com.ahcloud.edas.admin.biz.domain.dept.query.SysDeptTreeSelectQuery;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptTreeSelectVo;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptTreeVo;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptVo;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDept;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDeptRelation;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-18 16:32
 **/
@Slf4j
@Component
public class SysDeptManager {
    @Resource
    private SysDeptHelper sysDeptHelper;
    @Resource
    private SysDeptExtService sysDeptExtService;
    @Resource
    private SysDeptRelationService sysDeptRelationService;

    /**
     * 添加部门信息
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addDept(DeptAddForm form) {
        SysDept existedSysDept = sysDeptExtService.getOneByCode(form.getDeptCode());
        if (Objects.nonNull(existedSysDept)) {
            throw new BizException(ErrorCodeEnum.DEPT_CODE_IS_EXISTED, form.getDeptCode());
        }
        SysDept sysDept = sysDeptHelper.convert(form);
        sysDeptExtService.save(sysDept);

        // 设置子父节点关联关系
        List<SysDeptRelation> sysDeptRelations = sysDeptRelationService.list(
                new QueryWrapper<SysDeptRelation>().lambda()
                        .eq(SysDeptRelation::getDescendantCode, sysDept.getParentDeptCode())
                        .eq(SysDeptRelation::getDeleted, DeletedEnum.NO.value)
        );

        String userNameBySession = UserUtils.getUserNameBySession();
        List<SysDeptRelation> sysDeptRelationList = Lists.newArrayList();
        SysDeptRelation ownSysDeptRelation = new SysDeptRelation();
        ownSysDeptRelation.setAncestorCode(sysDept.getDeptCode());
        ownSysDeptRelation.setDescendantCode(sysDept.getDeptCode());
        sysDeptRelationList.add(ownSysDeptRelation);
        if (CollectionUtils.isNotEmpty(sysDeptRelations)) {
            for (SysDeptRelation sysDeptRelation : sysDeptRelations) {
                SysDeptRelation newSysDeptRelation = new SysDeptRelation();
                newSysDeptRelation.setDescendantCode(sysDept.getDeptCode());
                newSysDeptRelation.setAncestorCode(sysDeptRelation.getAncestorCode());
                newSysDeptRelation.setCreator(userNameBySession);
                newSysDeptRelation.setModifier(userNameBySession);
                sysDeptRelationList.add(newSysDeptRelation);
            }
            sysDeptRelationService.saveBatch(sysDeptRelationList);
        }
    }

    /**
     * 更新部门
     * @param form
     */
    public void updateSysDept(DeptUpdateForm form) {
        SysDept existedSysDept = sysDeptExtService.getOne(
                new QueryWrapper<SysDept>().lambda()
                        .eq(SysDept::getId, form.getId())
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDept)) {
            throw new BizException(ErrorCodeEnum.DEPT_NOT_EXISTED);
        }
        SysDept updateSysDept = new SysDept();
        updateSysDept.setName(form.getName());
        updateSysDept.setDeptOrder(form.getDeptOrder());
        updateSysDept.setId(form.getId());
        sysDeptExtService.updateById(updateSysDept);
    }

    /**
     * 删除系统部门
     * @param id
     */
    public void deleteSysDept(Long id) {
        SysDept existedSysDept = sysDeptExtService.getOne(
                new QueryWrapper<SysDept>().lambda()
                        .eq(SysDept::getId, id)
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDept)) {
            throw new BizException(ErrorCodeEnum.DEPT_NOT_EXISTED);
        }
        SysDept deleteSysDept = new SysDept();
        deleteSysDept.setDeleted(existedSysDept.getId());
        deleteSysDept.setId(existedSysDept.getId());
        sysDeptExtService.updateById(deleteSysDept);

        // 删除关联信息
        sysDeptRelationService.remove(
                new QueryWrapper<SysDeptRelation>().lambda()
                        .eq(SysDeptRelation::getDescendantCode, existedSysDept.getDeptCode())
        );
    }

    /**
     * 根据id查询系统部门信息
     * @param id
     * @return
     */
    public SysDeptVo findById(Long id) {
        SysDept existedSysDept = sysDeptExtService.getOne(
                new QueryWrapper<SysDept>().lambda()
                        .eq(SysDept::getId, id)
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedSysDept)) {
            throw new BizException(ErrorCodeEnum.DEPT_NOT_EXISTED);
        }
        return sysDeptHelper.convertToVo(existedSysDept);
    }

    /**
     * 查询部门树
     * @param query
     * @return
     */
    public List<SysDeptTreeVo> listSysDeptTree(SysDeptQuery query) {
        List<SysDept> sysDeptList = sysDeptExtService.list(
                new QueryWrapper<SysDept>().lambda()
                        .eq(StringUtils.isNotBlank(query.getDeptCode()), SysDept::getDeptCode, query.getDeptCode())
                        .eq(StringUtils.isNotBlank(query.getDeptName()), SysDept::getName, query.getDeptName())
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
        return sysDeptHelper.convertToTree(sysDeptList);
    }

    /**
     * 获取部门选择树
     *
     * @param query
     * @return
     */
    public List<SysDeptTreeSelectVo> listDeptSelectTree(SysDeptTreeSelectQuery query) {
        List<SysDept> sysDeptList = sysDeptExtService.list(
                new QueryWrapper<SysDept>().lambda()
                        .eq(SysDept::getDeleted, DeletedEnum.NO.value)
        );
        return sysDeptHelper.convertToTreeSelectVo(sysDeptList);
    }

    /**
     * 根据部门编码获取当前部门名称
     * @param deptCode
     * @return
     */
    public String getDeptNameByCode(String deptCode) {
        if (StringUtils.isBlank(deptCode)) {
            log.error("SysDeptManager[getDeptNameByCode] deptCode is empty");
            return AdminConstants.STR_EMPTY;
        }
        SysDept sysDept = sysDeptExtService.getOneByCode(deptCode);
        if (Objects.isNull(sysDept)) {
            log.error("SysDeptManager[getDeptNameByCode] dept info not existed, deptCode is {}", deptCode);
            return AdminConstants.STR_EMPTY;
        }
        return sysDept.getName();
    }
}
