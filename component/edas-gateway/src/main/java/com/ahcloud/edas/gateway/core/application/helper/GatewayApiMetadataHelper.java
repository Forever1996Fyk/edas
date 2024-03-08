package com.ahcloud.edas.gateway.core.application.helper;

import com.ahcloud.edas.gateway.core.domain.api.vo.ServiceIdSelectVO;
import com.ahcloud.edas.gateway.core.infrastructure.repository.bean.GatewayApiMetaData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ahcloud-gateway
 * @description: 网关接口元数据
 * @author: YuKai Fan
 * @create: 2023/5/29 16:41
 **/
public class GatewayApiMetadataHelper {


    public static List<ServiceIdSelectVO> convertToSelectVOList(List<GatewayApiMetaData> list) {
        return list.stream().map(gatewayApiMetaData ->
                    ServiceIdSelectVO.builder()
                            .serviceId(gatewayApiMetaData.getAppName())
                            .env(gatewayApiMetaData.getEnv())
                            .build()
                ).collect(Collectors.toList());
    }
}
