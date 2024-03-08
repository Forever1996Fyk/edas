package com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.event;

import com.ahcloud.edas.gateway.core.infrastructure.gateway.enums.ConfigGroupEnum;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.enums.DataEventTypeEnum;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/6 15:33
 **/
@Getter
public class DataChangedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 4226617684506700868L;

    private final DataEventTypeEnum eventType;

    private final ConfigGroupEnum groupKey;

    /**
     * 环境变量集合
     */
    private List<String> envList;

    public DataChangedEvent(Object source, DataEventTypeEnum eventType, ConfigGroupEnum groupKey) {
        super(Lists.newArrayList(source));
        this.eventType = eventType;
        this.groupKey = groupKey;
    }

    public DataChangedEvent(Object source, List<String> envList, DataEventTypeEnum eventType, ConfigGroupEnum groupKey) {
        super(Lists.newArrayList(source));
        this.eventType = eventType;
        this.envList = envList;
        this.groupKey = groupKey;
    }

    @Override
    public List<?> getSource() {
        return (List<?>) super.getSource();
    }

}
