package com.ahcloud.edas.nacos.biz.infrastructure.constant.enums;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 17:22
 **/
public enum NacosPathEnum {

    /**
     * 请求地址
     */
    AUTH("/v1/auth/login", "/v1/auth/login"),
    CONFIG("/v1/cs/configs", "/v2/cs/config"),
    HISTORY_LIST("/v1/cs/history", "/v2/cs/history/list"),
    HISTORY("/v1/cs/history", "/v2/cs/history"),
    HISTORY_PREVIOUS("/v1/cs/history/previous", "/v2/cs/history/previous"),
    NAMESPACE("/v1/console/namespaces", "/v2/console/namespace/list"),
    ;
    private final String pathV1;
    private final String pathV2;

    NacosPathEnum(String pathV1, String pathV2) {
        this.pathV1 = pathV1;
        this.pathV2 = pathV2;
    }

    public String getPathV1() {
        return pathV1;
    }

    public String getPathV2() {
        return pathV2;
    }

    public String getPath() {
        return pathV1;
    }
}
