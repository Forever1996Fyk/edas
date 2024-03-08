package com.ahcloud.edas.nacos.biz.infrastructure.nacos;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.HistoryConfigInfo;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.NacosAuthInfo;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.NacosPageResult;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.NamespaceInfo;
import com.ahcloud.edas.nacos.biz.infrastructure.config.NacosProperties;
import com.ahcloud.edas.nacos.biz.infrastructure.constant.enums.NacosPathEnum;
import com.ahcloud.edas.nacos.biz.infrastructure.constant.enums.NacosRetCodeEnum;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.client.utils.ParamUtil;
import com.alibaba.nacos.common.constant.HttpHeaderConsts;
import com.alibaba.nacos.common.http.HttpRestResult;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.http.param.Query;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.alibaba.nacos.common.utils.VersionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:47
 **/
public class NacosOpenApiClient {

    private final NacosProperties properties;
    private final NacosRestTemplate restTemplate;

    public NacosOpenApiClient(NacosConnector connector) {
        this.properties = connector.getProperties();
        this.restTemplate = connector.getRestTemplate();
    }


    /**
     * 获取认证token
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        Map<String, String> params = Maps.newHashMap();
        params.put("username", properties.getUsername());
        params.put("password", properties.getPassword());
        Query query = Query.newInstance().initParams(params);
        HttpRestResult<NacosAuthInfo> result = restTemplate.post(buildUrl(NacosPathEnum.AUTH), getSpasHeaders(), query, null, NacosAuthInfo.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_AUTH);
        }
        NacosAuthInfo nacosAuthInfo = result.getData();
        if (Objects.isNull(nacosAuthInfo)) {
            throw new BizException(NacosRetCodeEnum.NACOS_AUTH);
        }
        return nacosAuthInfo.getAccessToken();
    }

    /**
     * 发布配置
     * @param namespace
     * @param dataId
     * @param group
     * @param content
     * @param type
     * @throws Exception
     */
    public void publishConfig(String namespace, String dataId, String group, String content, String type) throws Exception {
        String accessToken = getAccessToken();
        Map<String, String> params = Maps.newHashMap();
        params.put("dataId", dataId);
        params.put("group", group);
        params.put("content", content);
        params.put("accessToken", accessToken);
        Query query = Query.newInstance().initParams(params);
        if (StringUtils.isNotBlank(namespace)) {
            // v1
            query.addParam("tenant", namespace);
            //v2
            query.addParam("namespaceId", namespace);
        }
        if (StringUtils.isNotBlank(type)) {
            query.addParam("type", type);
        }
        HttpRestResult<Boolean> result = restTemplate.post(buildUrl(NacosPathEnum.CONFIG), getSpasHeaders(), query, null, Boolean.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
        if (Objects.isNull(result.getData())) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, "data is null");
        }
        if (!result.getData()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
    }

    /**
     * 删除配置
     * @param namespace
     * @param dataId
     * @param group
     */
    public void deleteConfig(String namespace, String dataId, String group) throws Exception {
        String accessToken = getAccessToken();
        Map<String, String> params = Maps.newHashMap();
        params.put("dataId", dataId);
        params.put("group", group);
        params.put("accessToken", accessToken);
        Query query = Query.newInstance().initParams(params);
        if (StringUtils.isNotBlank(namespace)) {
            // v1
            query.addParam("tenant", namespace);
            //v2
            query.addParam("namespaceId", namespace);
        }
        HttpRestResult<Boolean> result = restTemplate.delete(buildUrl(NacosPathEnum.CONFIG), getSpasHeaders(), query, Boolean.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
        if (Objects.isNull(result.getData())) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, "data is null");
        }
        if (!result.getData()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
    }

    /**
     * 获取配置
     * @param namespace
     * @param dataId
     * @param group
     * @return
     * @throws Exception
     */
    public String getConfig(String namespace, String dataId, String group) throws Exception {
        String accessToken = getAccessToken();
        Map<String, String> params = Maps.newHashMap();
        params.put("dataId", dataId);
        params.put("group", group);
        params.put("accessToken", accessToken);
        Query query = Query.newInstance().initParams(params);
        if (StringUtils.isNotBlank(namespace)) {
            // v1
            query.addParam("tenant", namespace);
            //v2
            query.addParam("namespaceId", namespace);
        }
        HttpRestResult<String> result = restTemplate.get(buildUrl(NacosPathEnum.CONFIG), getSpasHeaders(), query, String.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
        if (Objects.isNull(result.getData())) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, "data is null");
        }
        return result.getData();
    }

    /**
     * 分页查询历史配置列表
     *
     * @param appId
     * @param namespace
     * @param dataId
     * @param group
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public NacosPageResult<HistoryConfigInfo> queryHistoryList(Long appId, String namespace, String dataId, String group, Integer pageNo, Integer pageSize) throws Exception {
        String accessToken = getAccessToken();
        Map<String, String> params = Maps.newHashMap();
        params.put("dataId", dataId);
        params.put("group", group);
        params.put("search", "accurate");
        params.put("accessToken", accessToken);
        Query query = Query.newInstance().initParams(params);
        if (StringUtils.isNotBlank(namespace)) {
            // v1
            query.addParam("tenant", namespace);
            //v2
            query.addParam("namespaceId", namespace);
        }
        if (pageNo != null) {
            query.addParam("pageNo", pageNo);
        }
        if (pageSize != null) {
            query.addParam("pageSize", pageSize);
        }
        HttpRestResult<String> result = restTemplate.get(buildUrl(NacosPathEnum.HISTORY_LIST), getSpasHeaders(), query, String.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
        if (Objects.isNull(result.getData())) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, "data is null");
        }
        NacosPageResult<HistoryConfigInfo> nacosPageResult = JsonUtils.stringToBean(result.getData(), new TypeReference<>() {});
        if (Objects.isNull(nacosPageResult)) {
            return null;
        }
        List<HistoryConfigInfo> pageItems = nacosPageResult.getPageItems();
        for (HistoryConfigInfo pageItem : pageItems) {
            pageItem.setAppId(appId);
            if (StringUtils.isNotBlank(pageItem.getCreatedTime())) {
                pageItem.setCreatedDateTime(DateUtils.formatSpecial(pageItem.getCreatedTime(), DateUtils.DatePattern.PATTERN0));
            }
            if (StringUtils.isNotBlank(pageItem.getLastModifiedTime())) {
                pageItem.setLastModifiedDateTime(DateUtils.formatSpecial(pageItem.getLastModifiedTime(), DateUtils.DatePattern.PATTERN0));
            }
            pageItem.setOpTypeName(switchOpType(pageItem.getOpType()));
        }
        return nacosPageResult;
    }

    private String switchOpType(String opType) {
        switch (StringUtils.deleteWhitespace(opType)) {
            case "I":
                return "新增";
            case "D":
                return "删除";
            case "U":
                return "更新";
            default:
                return "未知";
        }
    }

    /**
     * 分页查询历史配置列表
     * @param namespace
     * @param dataId
     * @param group
     * @return
     * @throws Exception
     */
    public HistoryConfigInfo queryHistoryById(String namespace, String dataId, String group, String nid) throws Exception {
        String accessToken = getAccessToken();
        Map<String, String> params = Maps.newHashMap();
        params.put("dataId", dataId);
        params.put("group", group);
        params.put("nid", nid);
        params.put("accessToken", accessToken);
        Query query = Query.newInstance().initParams(params);
        if (StringUtils.isNotBlank(namespace)) {
            // v1
            query.addParam("tenant", namespace);
            // v2
            query.addParam("namespaceId", namespace);
        }
        HttpRestResult<HistoryConfigInfo> result = restTemplate.get(buildUrl(NacosPathEnum.HISTORY), getSpasHeaders(), query, HistoryConfigInfo.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, result.getMessage());
        }
        if (Objects.isNull(result.getData())) {
            throw new BizException(NacosRetCodeEnum.NACOS_CONFIG_WITH_REASON, "data is null");
        }
        return result.getData();
    }

    /**
     * 查询所有命名空间
     * @return
     * @throws Exception
     */
    public List<NamespaceInfo> queryNamespaceList() throws Exception {
        String accessToken = getAccessToken();
        Map<String, String> params = Maps.newHashMap();
        params.put("accessToken", accessToken);
        Query query = Query.newInstance().initParams(params);
        HttpRestResult<String> result = restTemplate.get(buildUrl(NacosPathEnum.NAMESPACE), getSpasHeaders(), query, String.class);
        if (!result.ok()) {
            throw new BizException(NacosRetCodeEnum.NACOS_NAMESPACE, result.getMessage());
        }
        if (Objects.isNull(result.getData())) {
            throw new BizException(NacosRetCodeEnum.NACOS_NAMESPACE, "data is null");
        }
        String data = result.getData();
        HttpRestResult<List<NamespaceInfo>> restResult = JsonUtils.stringToBean(data, new TypeReference<>() {
        });
        return restResult.getData();
    }


    /**
     * 构建url
     * @param path
     * @return
     */
    private String buildUrl(NacosPathEnum path) {
        return properties.getUrl() + path.getPath();
    }

    /**
     * 生成heander
     * @return
     */
    private Header getSpasHeaders() {
        Header header = Header.newInstance();
        String ts = String.valueOf(System.currentTimeMillis());
        header.addParam(Constants.CLIENT_APPNAME_HEADER, ParamUtil.getAppName());
        header.addParam(Constants.CLIENT_REQUEST_TS_HEADER, ts);
        header.addParam(HttpHeaderConsts.CLIENT_VERSION_HEADER, VersionUtils.version);
        header.addParam("exConfigInfo", "true");
        header.addParam(HttpHeaderConsts.REQUEST_ID, UuidUtils.generateUuid());
        header.addParam(HttpHeaderConsts.ACCEPT_CHARSET, "UTF-8");
        return header;
    }
}
