package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictAddForm;
import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictUpdateForm;
import com.ahcloud.edas.admin.biz.domain.dict.vo.SysDictVo;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDict;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:23
 **/
@Component
public class SysDictHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDict convert(SysDictAddForm form) {
        SysDict sysDict = Convert.INSTANCE.convert(form);
        sysDict.setCreator(UserUtils.getUserNameBySession());
        sysDict.setModifier(UserUtils.getUserNameBySession());
        return sysDict;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDict convert(SysDictUpdateForm form) {
        SysDict sysDict = Convert.INSTANCE.convert(form);
        sysDict.setModifier(UserUtils.getUserNameBySession());
        return sysDict;
    }

    /**
     * 数据转换
     * @param sysDict
     * @return
     */
    public SysDictVo convertToVo(SysDict sysDict) {
        return Convert.INSTANCE.convertToVo(sysDict);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<SysDictVo> convertToPageResult(PageInfo<SysDict> pageInfo) {
        PageResult<SysDictVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Mapper
    public interface Convert {
        Convert INSTANCE = Mappers.getMapper(Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDict convert(SysDictAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDict convert(SysDictUpdateForm form);

        /**
         * 数据转换
         * @param sysDict
         * @return
         */
        @Mappings({
                @Mapping(target = "changeName", expression = "java(com.ahcloud.edas.common.enums.YesOrNoEnum.getByType(sysDict.getChanged()).getDesc())"),
        })
        SysDictVo convertToVo(SysDict sysDict);

        /**
         * 数据转换
         * @param sysDictList
         * @return
         */
        List<SysDictVo> convertToVoList(List<SysDict> sysDictList);
    }
}
