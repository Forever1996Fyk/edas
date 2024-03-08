package com.ahcloud.edas.gateway.core.application.manager;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.gateway.core.application.service.GatewayApiMetaDataService;
import com.ahcloud.edas.gateway.core.domain.api.vo.ApiMetadataVO;
import com.ahcloud.edas.gateway.core.infrastructure.constant.enums.GatewayRetCodeEnum;
import com.ahcloud.edas.gateway.core.infrastructure.register.instance.GatewayClientServerInstanceService;
import com.ahcloud.edas.gateway.core.infrastructure.repository.bean.GatewayApiMetaData;
import com.ahcloud.edas.gateway.core.infrastructure.repository.bean.SysAppPark;
import com.ahcloud.edas.gateway.core.infrastructure.repository.mapper.SysAppParkMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/5/29 17:27
 **/
@Component
public class GatewayApiMetadataManager {
    @Resource
    private SysAppParkMapper sysAppParkMapper;
    @Resource
    private GatewayApiMetaDataService gatewayApiMetaDataService;
    @Resource
    private GatewayClientServerInstanceService gatewayClientServerInstanceService;

    /**
     * 获取api元数据选择列表
     * @param appId
     * @return
     */
    public List<ApiMetadataVO> selectApiMetadataList(Long appId) {
        if (appId == null) {
            throw new BizException(GatewayRetCodeEnum.PARAM_ILLEGAL_FIELD, "appId");
        }
        SysAppPark sysAppPark = sysAppParkMapper.selectOne(
                new QueryWrapper<SysAppPark>().lambda()
                        .eq(SysAppPark::getAppId, appId)
                        .eq(SysAppPark::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysAppPark)) {
            throw new BizException(GatewayRetCodeEnum.APP_DATA_NOT_EXISTED);
        }
        String appCode = sysAppPark.getAppCode();
        String env = sysAppPark.getEnv();
        if (hasNoHealthInstance(appCode, env)) {
            throw new BizException(GatewayRetCodeEnum.APP_INSTANCE_ERROR, env, appCode);
        }
        List<GatewayApiMetaData> list = gatewayApiMetaDataService.list(
                new QueryWrapper<GatewayApiMetaData>().lambda()
                        .eq(GatewayApiMetaData::getAppId, appId)
                        .eq(GatewayApiMetaData::getDeleted, DeletedEnum.NO.value)

        );
        return list.stream()
                .map(gatewayApiMetaData ->
                        ApiMetadataVO.builder()
                                .id(gatewayApiMetaData.getId())
                                .apiPath(gatewayApiMetaData.getApiPath())
                                .appName(gatewayApiMetaData.getAppName())
                                .httpMethod(gatewayApiMetaData.getHttpMethod())
                                .className(gatewayApiMetaData.getQualifiedName() + "." + gatewayApiMetaData.getMethodName())
                                .serviceId(gatewayApiMetaData.getServiceId())
                                .qualifiedName(gatewayApiMetaData.getQualifiedName())
                                .methodName(gatewayApiMetaData.getMethodName())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private boolean hasNoHealthInstance(String appName, String env) {
        return gatewayClientServerInstanceService.checkHealthInstance(appName, env);
    }

}
