package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictDetailAddForm;
import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictDetailUpdateForm;
import com.ahcloud.edas.admin.biz.domain.dict.vo.SysDictDetailVo;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysDictDetail;
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
 * @create: 2022-07-19 17:01
 **/
@Component
public class SysDictDetailHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDictDetail convert(SysDictDetailAddForm form) {
        SysDictDetail sysDictDetail = Convert.INSTANCE.convert(form);
        sysDictDetail.setCreator(UserUtils.getUserNameBySession());
        sysDictDetail.setModifier(UserUtils.getUserNameBySession());
        return sysDictDetail;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysDictDetail convert(SysDictDetailUpdateForm form) {
        SysDictDetail sysDictDetail = Convert.INSTANCE.convert(form);
        sysDictDetail.setModifier(UserUtils.getUserNameBySession());
        return sysDictDetail;
    }

    /**
     * 数据转换
     * @param sysDictDetail
     * @return
     */
    public SysDictDetailVo convertToVo(SysDictDetail sysDictDetail) {
        return Convert.INSTANCE.convertToVo(sysDictDetail);
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public PageResult<SysDictDetailVo> convertToPageResult(PageInfo<SysDictDetail> pageInfo) {
        PageResult<SysDictDetailVo> pageResult = new PageResult<>();
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setRows(Convert.INSTANCE.convertToVoList(pageInfo.getList()));
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
        SysDictDetail convert(SysDictDetailAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysDictDetail convert(SysDictDetailUpdateForm form);

        /**
         * 数据转换
         * @param sysDictDetail
         * @return
         */
        @Mappings({
                @Mapping(target = "editName", expression = "java(com.ahcloud.edas.common.enums.YesOrNoEnum.getByType(sysDictDetail.getEdit()).getDesc())"),
        })
        SysDictDetailVo convertToVo(SysDictDetail sysDictDetail);

        /**
         * 数据转换
         * @param sysDictDetailList
         * @return
         */
        List<SysDictDetailVo> convertToVoList(List<SysDictDetail> sysDictDetailList);
    }
}
