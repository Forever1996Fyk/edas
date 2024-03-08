package com.ahcloud.edas.gateway.core.application.helper;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.gateway.core.domain.api.dto.ApiDefinitionDTO;
import com.ahcloud.edas.gateway.core.domain.api.form.ApiAddForm;
import com.ahcloud.edas.gateway.core.domain.api.form.ApiUpdateForm;
import com.ahcloud.edas.gateway.core.domain.api.vo.ApiVO;
import com.ahcloud.edas.gateway.core.infrastructure.constant.enums.ApiStatusEnum;
import com.ahcloud.edas.gateway.core.infrastructure.repository.bean.GatewayApi;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/4 11:03
 **/
public class GatewayApiHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static GatewayApi convert(ApiAddForm form) {
        GatewayApi gatewayApi = Convert.INSTANCE.convert(form);
        gatewayApi.setCreator("system");
        gatewayApi.setModifier("system");
        return gatewayApi;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static GatewayApi convert(ApiUpdateForm form) {
        return Convert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     * @param gatewayApi
     * @return
     */
    public static ApiVO convertToVo(GatewayApi gatewayApi) {
        ApiVO apiVO = Convert.INSTANCE.convertToVo(gatewayApi);
        apiVO.setClassName(
                gatewayApi.getQualifiedName() + "." + gatewayApi.getMethodName()
        );
        return apiVO;
    }

    /**
     * 数据转换
     * @param pageInfo
     * @return
     */
    public static PageResult<ApiVO> convertToPageResult(PageInfo<GatewayApi> pageInfo) {
        List<GatewayApi> list = pageInfo.getList();
        List<ApiVO> apiVOList = Convert.INSTANCE.convertToVoList(list);
        PageResult<ApiVO> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setRows(apiVOList);
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    /**
     * 数据转换
     * @param gatewayApi
     * @return
     */
    public static ApiDefinitionDTO convertToDTO(GatewayApi gatewayApi) {
        return ApiDefinitionDTO.builder()
                .apiCode(gatewayApi.getApiCode())
                .auth(gatewayApi.getAuth() != null && gatewayApi.getAuth() == 1)
                .path(gatewayApi.getApiPath())
                .dev(gatewayApi.getDev())
                .test(gatewayApi.getTest())
                .sit(gatewayApi.getSit())
                .pre(gatewayApi.getPre())
                .prod(gatewayApi.getProd())
                .build();
    }

    /**
     * 数据转换
     * @param gatewayApiList
     * @return
     */
    public static List<ApiDefinitionDTO> convertToDTOList(List<GatewayApi> gatewayApiList) {
        return gatewayApiList.stream().map(GatewayApiHelper::convertToDTO).collect(Collectors.toList());
    }

    @Mapper
    public interface Convert {
        Convert INSTANCE = Mappers.getMapper(Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        GatewayApi convert(ApiAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        GatewayApi convert(ApiUpdateForm form);

        /**
         * 数据转换
         * @param gatewayApi
         * @return
         */
        @Mappings({
                @Mapping(target = "className", expression = "java(gatewayApi.getQualifiedName() + gatewayApi.getMethodName())")
        })
        ApiVO convertToVo(GatewayApi gatewayApi);

        /**
         * 数据转换
         * @param gatewayApiList
         * @return
         */
        List<ApiVO> convertToVoList(List<GatewayApi> gatewayApiList);
    }
}
