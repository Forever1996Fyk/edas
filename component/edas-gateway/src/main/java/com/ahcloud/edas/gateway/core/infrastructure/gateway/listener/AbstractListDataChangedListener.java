package com.ahcloud.edas.gateway.core.infrastructure.gateway.listener;


import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.gateway.core.domain.route.dto.RouteDefinitionDTO;
import com.ahcloud.edas.gateway.core.domain.sync.ApiSyncData;
import com.ahcloud.edas.gateway.core.domain.sync.RouteSyncData;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.enums.DataEventTypeEnum;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 15:53
 **/
public abstract class AbstractListDataChangedListener implements DataChangeListener {

    private final ChangeData changeData;

    protected AbstractListDataChangedListener(ChangeData changeData) {
        this.changeData = changeData;
    }

    @Override
    public void onRemoteRouteDefinitionChanged(List<RouteDefinitionDTO> routeDefinitionList, DataEventTypeEnum eventType) {
        List<RouteSyncData> routeSyncDataList = routeDefinitionList.stream()
                .map(routeDefinitionDTO -> new RouteSyncData(routeDefinitionDTO.getId(), eventType, routeDefinitionDTO.getEnv())).collect(Collectors.toList());
        publishConfig(changeData.getRouteDataId(), routeSyncDataList);
    }

    @Override
    public void onApiRefreshChanged(List<ImmutablePair<String, Integer>> apiList, List<String> envList, DataEventTypeEnum eventType) {
        if (CollectionUtils.isNotEmpty(envList)) {
            List<ApiSyncData> apiSyncDataList = apiList.stream()
                    .map(api -> new ApiSyncData(api.getLeft(), api.getRight(), eventType))
                    .collect(Collectors.toList());
            for (String env : envList) {
                publishConfig(env, changeData.getApiDataId(), apiSyncDataList);
            }
        }
    }

    /**
     * 发布配置
     * @param dataId
     * @param data
     */
    protected abstract void publishConfig(String dataId, Object data);

    /**
     * 发布配置
     * @param env
     * @param dataId
     * @param data
     */
    protected abstract void publishConfig(String env, String dataId, Object data);


    public static class ChangeData {

        /**
         * route definition data id
         */
        private final String routeDataId;

        private final String apiDataId;

        public ChangeData(String routeDataId, String apiDataId) {
            this.routeDataId = routeDataId;
            this.apiDataId = apiDataId;
        }

        public String getRouteDataId() {
            return routeDataId;
        }

        public String getApiDataId() {
            return apiDataId;
        }
    }
}
