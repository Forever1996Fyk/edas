package com.ahcloud.edas.gateway.core.infrastructure.gateway.listener;

import com.ahcloud.edas.gateway.core.domain.route.dto.RouteDefinitionDTO;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.event.DataChangedEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 22:36
 **/
@Component
public class DataChangeEventDispatcher implements ApplicationListener<DataChangedEvent>, InitializingBean {

    private final ApplicationContext applicationContext;

    private List<DataChangeListener> listeners;

    public DataChangeEventDispatcher(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(final DataChangedEvent event) {
        for (DataChangeListener listener : listeners) {
            switch (event.getGroupKey()) {
                case REMOTE_ROUTE:
                    listener.onRemoteRouteDefinitionChanged((List<RouteDefinitionDTO>) event.getSource(), event.getEventType());
                    break;
                case API:
                    listener.onApiRefreshChanged((List<ImmutablePair<String, Integer>>) event.getSource(), event.getEnvList(), event.getEventType());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + event.getGroupKey());
            }
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<DataChangeListener> listenerBeans = applicationContext.getBeansOfType(DataChangeListener.class).values();
        this.listeners = Collections.unmodifiableList(new ArrayList<>(listenerBeans));
    }
}
