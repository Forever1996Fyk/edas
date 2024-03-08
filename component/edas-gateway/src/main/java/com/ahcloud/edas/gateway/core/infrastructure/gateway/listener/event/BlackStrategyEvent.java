package com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.server.ServerWebExchange;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/6/15 16:55
 **/
@Getter
public class BlackStrategyEvent extends ApplicationEvent {
    private static final long serialVersionUID = 7882406035735149153L;

    private ServerWebExchange exchange;
    private String reason;

    public BlackStrategyEvent(Object source) {
        super(source);
    }

    public BlackStrategyEvent(ServerWebExchange exchange) {
        super(exchange);
        this.exchange = exchange;
    }


    public BlackStrategyEvent(ServerWebExchange exchange, String reason) {
        super(exchange);
        this.exchange = exchange;
        this.reason = reason;
    }

}
