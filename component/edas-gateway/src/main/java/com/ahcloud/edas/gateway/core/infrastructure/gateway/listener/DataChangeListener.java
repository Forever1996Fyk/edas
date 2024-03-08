package com.ahcloud.edas.gateway.core.infrastructure.gateway.listener;


import com.ahcloud.edas.gateway.core.domain.route.dto.RouteDefinitionDTO;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.enums.DataEventTypeEnum;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 15:39
 **/
public interface DataChangeListener {

    /**
     * 远程路由变更
     *
     * @param routeDefinitionList
     * @param eventType
     */
    default void onRemoteRouteDefinitionChanged(List<RouteDefinitionDTO> routeDefinitionList, DataEventTypeEnum eventType) {

    }

    /**
     * api接口变更
     * @param apiList
     * @param envList
     * @param eventType
     */
    default void onApiRefreshChanged(List<ImmutablePair<String, Integer>> apiList, List<String> envList, DataEventTypeEnum eventType) {

    }
}
