package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.YesOrNoEnum;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.ahcloud.edas.uaa.starter.core.constant.enums.ApiStatusEnum;
import com.ahcloud.edas.uaa.starter.core.constant.enums.ReadOrWriteEnum;
import com.ahcloud.edas.uaa.starter.domain.AuthorityApiDTO;
import com.ahcloud.edas.admin.biz.domain.api.form.SysApiAddForm;
import com.ahcloud.edas.admin.biz.domain.api.form.SysApiUpdateForm;
import com.ahcloud.edas.admin.biz.domain.api.vo.SysApiVo;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysApi;
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
 * @create: 2021-12-24 15:56
 **/
@Component
public class SysApiHelper {

    /**
     * 数据转换
     *
     * @param list
     * @return
     */
    public List<AuthorityApiDTO> convertToDTO(List<SysApi> list) {
        return SysApiHelperConvert.INSTANCE.convert(list);
    }

    public SysApi convertToEntity(SysApiAddForm form) {
        SysApi sysApi = SysApiHelperConvert.INSTANCE.convert(form);
        sysApi.setCreator(UserUtils.getUserNameBySession());
        sysApi.setModifier(UserUtils.getUserNameBySession());
        return sysApi;
    }

    public SysApi convertToEntity(SysApiUpdateForm form) {
        return SysApiHelperConvert.INSTANCE.convert(form);
    }

    public SysApiVo convertToVo(SysApi sysApi) {
        return SysApiHelperConvert.INSTANCE.convertToVO(sysApi);
    }

    public PageResult<SysApiVo> convertToPageResult(PageInfo<SysApi> pageInfo) {
        PageResult<SysApiVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysApiHelperConvert.INSTANCE.convertToVO(pageInfo.getList()));
        result.setPageSize(pageInfo.getPageSize());
        return result;
    }

    @Mapper(uses = {ApiStatusEnum.class, ReadOrWriteEnum.class})
    public interface SysApiHelperConvert {
        SysApiHelperConvert INSTANCE = Mappers.getMapper(SysApiHelperConvert.class);

        /**
         * 数据转换
         *
         * @param sysApi
         * @return
         */
        @Mappings({
                @Mapping(target = "apiStatusEnum", source = "status"),
                @Mapping(target = "readOrWriteEnum", source = "readOrWrite"),
                @Mapping(target = "uri", source = "apiUrl"),
                @Mapping(target = "auth", expression = "java(com.ahcloud.edas.common.enums.YesOrNoEnum.getByType(sysApi.getAuth()))"),
                @Mapping(target = "open", expression = "java(com.ahcloud.edas.common.enums.YesOrNoEnum.getByType(sysApi.getOpen()))"),
                @Mapping(target = "global", expression = "java(com.ahcloud.edas.common.enums.YesOrNoEnum.getByType(sysApi.getGlobal()))")
        })
        AuthorityApiDTO convert(SysApi sysApi);

        /**
         * 数据转换
         *
         * @param sysApis
         * @return
         */
        List<AuthorityApiDTO> convert(List<SysApi> sysApis);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        SysApi convert(SysApiAddForm form);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        SysApi convert(SysApiUpdateForm form);

        /**
         * 数据转换
         *
         * @param sysApi
         * @return
         */
        @Mappings({
                @Mapping(target = "statusName", expression = "java(com.ahcloud.edas.uaa.starter.core.constant.enums.ApiStatusEnum.valueOf(sysApi.getStatus()).getDesc())"),
                @Mapping(target = "apiTypeName", expression = "java(com.ahcloud.edas.admin.biz.infrastructure.constant.enums.SysApiTypeEnum.getByType(sysApi.getApiType()).getDesc())")
        })
        SysApiVo convertToVO(SysApi sysApi);

        /**
         * 数据转换
         *
         * @param sysApiList
         * @return
         */
        List<SysApiVo> convertToVO(List<SysApi> sysApiList);
    }
}
