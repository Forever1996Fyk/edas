package com.ahcloud.edas.nacos.biz.application.manager;

import cn.hutool.core.convert.Convert;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.admin.biz.application.service.NacosConfigService;
import com.ahcloud.edas.nacos.biz.application.service.AppResourceService;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.HistoryConfigInfo;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.NacosPageResult;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.NamespaceInfo;
import com.ahcloud.edas.nacos.biz.domain.nacos.form.ConfigAddForm;
import com.ahcloud.edas.nacos.biz.domain.nacos.form.ConfigUpdateForm;
import com.ahcloud.edas.nacos.biz.domain.nacos.query.ConfigQuery;
import com.ahcloud.edas.nacos.biz.domain.nacos.vo.ConfigVO;
import com.ahcloud.edas.nacos.biz.domain.nacos.vo.NamespaceSelectVO;
import com.ahcloud.edas.nacos.biz.infrastructure.constant.enums.NacosRetCodeEnum;
import com.ahcloud.edas.nacos.biz.infrastructure.nacos.NacosOpenApiClient;
import com.ahcloud.edas.nacos.biz.infrastructure.repository.bean.NacosConfig;
import com.ahcloud.edas.nacos.biz.infrastructure.repository.bean.AppResource;
import com.ahcloud.edas.nacos.biz.infrastructure.util.PageUtils;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/7 00:09
 **/
@Slf4j
@Component
public class NacosConfigManager {
    @Resource
    private NacosConfigService nacosConfigService;
    @Resource
    private NacosOpenApiClient nacosOpenApiClient;
    @Resource
    private AppResourceService appResourceService;

    /**
     * 新增配置
     * @param form
     */
    public void addConfig(ConfigAddForm form)  {
        NacosConfig existedConfig = nacosConfigService.getOne(
                new QueryWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getAppId, form.getAppId())
                        .eq(NacosConfig::getTenant, form.getTenant())
                        .eq(NacosConfig::getDataId, form.getDataId())
                        .eq(NacosConfig::getBizGroup, form.getBizGroup())
                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedConfig)) {
            throw new BizException(NacosRetCodeEnum.DATA_EXISTED);
        }
        NacosConfig nacosConfig = Convert.convert(NacosConfig.class, form);
        nacosConfig.setCreator(UserUtils.getUserNameBySession());
        nacosConfig.setModifier(UserUtils.getUserNameBySession());
        nacosConfigService.save(nacosConfig);

        try {
            // nacos 配置 敏感信息 掩码替换
            List<AppResource> appResources = listAppResourcesByAppId(form.getAppId());
            String actualContent = replaceConfigSensitiveInfo(appResources, form.getContent());
            nacosOpenApiClient.publishConfig(form.getTenant(), form.getDataId(), form.getBizGroup(), actualContent, form.getType());
        } catch (Exception e) {
            if (e instanceof BizException) {
                throw (BizException) e;
            }
            throw new BizException(NacosRetCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 更新配置
     * @param form
     */
    public void updateConfig(ConfigUpdateForm form) {
        NacosConfig existedConfig = nacosConfigService.getOne(
                new QueryWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, form.getId())
                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedConfig)) {
            throw new BizException(NacosRetCodeEnum.DATA_NOT_EXISTED);
        }
        NacosConfig updateConfig = Convert.convert(NacosConfig.class, form);
        updateConfig.setVersion(existedConfig.getVersion());
        nacosConfigService.update(
                updateConfig,
                new UpdateWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, form.getId())
                        .eq(NacosConfig::getVersion, existedConfig.getVersion())
        );
        try {
            // nacos 配置 敏感信息 掩码替换
            List<AppResource> appResources = listAppResourcesByAppId(existedConfig.getAppId());
            String actualContent = replaceConfigSensitiveInfo(appResources, form.getContent());
            nacosOpenApiClient.publishConfig(existedConfig.getTenant(), existedConfig.getDataId(), existedConfig.getBizGroup(), actualContent, existedConfig.getType());
        } catch (Exception e) {
            if (e instanceof BizException) {
                throw (BizException) e;
            }
            throw new BizException(NacosRetCodeEnum.SYSTEM_ERROR);

        }
    }

    /**
     * 删除配置
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long id) {
        NacosConfig nacosConfig = nacosConfigService.getOne(
                new QueryWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, id)
                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(nacosConfig)) {
            throw new BizException(NacosRetCodeEnum.DATA_NOT_EXISTED);
        }
        NacosConfig deleteNacosConfig = new NacosConfig();
        deleteNacosConfig.setDeleted(id);
        deleteNacosConfig.setVersion(nacosConfig.getVersion());
        nacosConfigService.update(
                deleteNacosConfig,
                new UpdateWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, id)
                        .eq(NacosConfig::getVersion, nacosConfig.getVersion())
        );
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    nacosOpenApiClient.deleteConfig(nacosConfig.getTenant(), nacosConfig.getDataId(), nacosConfig.getBizGroup());
                } catch (Exception e) {
                    if (e instanceof BizException) {
                        throw (BizException) e;
                    }
                    throw new BizException(NacosRetCodeEnum.SYSTEM_ERROR);

                }
            }
        });
    }

    /**
     * 根据id获取配置内容
     * @param id
     * @return
     */
    public ConfigVO findConfigById(Long id) {
        NacosConfig nacosConfig = nacosConfigService.getOne(
                new QueryWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, id)
                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(nacosConfig)) {
            throw new BizException(NacosRetCodeEnum.DATA_NOT_EXISTED);
        }
        try {
            String config = nacosOpenApiClient.getConfig(nacosConfig.getTenant(), nacosConfig.getDataId(), nacosConfig.getBizGroup());
            ConfigVO configVO = Convert.convert(ConfigVO.class, nacosConfig);
            // nacos 配置 掩码替换 敏感信息
            List<AppResource> appResources = listAppResourcesByAppId(nacosConfig.getAppId());
            String actualContent = reverseReplaceConfigSensitiveInfo(appResources, config);
            configVO.setContent(actualContent);
            return configVO;
        } catch (Exception e) {
            if (e instanceof BizException) {
                throw (BizException) e;
            } else {
                throw new BizException(NacosRetCodeEnum.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 分页查询配置列表
     * @param query
     * @return
     */
    public PageResult<ConfigVO> pageConfigList(ConfigQuery query) {
        PageInfo<NacosConfig> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> nacosConfigService.list(
                                new QueryWrapper<NacosConfig>().lambda()
                                        .eq(NacosConfig::getAppId, query.getAppId())
                                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
                                        .like(
                                                StringUtils.isNotBlank(query.getDataId()),
                                                NacosConfig::getDataId,
                                                query.getDataId()
                                        )
                                        .like(
                                                StringUtils.isNotBlank(query.getGroup()),
                                                NacosConfig::getBizGroup,
                                                query.getGroup()
                                        )
                        )
                );
        return PageUtils.pageInfoConvertToPageResult(pageInfo, Convert.toList(ConfigVO.class, pageInfo.getList()));
    }

    /**
     * 分页查询历史配置列表
     * @param query
     * @return
     * @throws Exception
     */
    public PageResult<HistoryConfigInfo> pageHistoryConfigList(ConfigQuery query) throws Exception {
        NacosConfig nacosConfig = nacosConfigService.getOne(
                new QueryWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, query.getId())
                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(nacosConfig)) {
            throw new BizException(NacosRetCodeEnum.DATA_NOT_EXISTED);
        }
        NacosPageResult<HistoryConfigInfo> result = nacosOpenApiClient.queryHistoryList(nacosConfig.getAppId(), nacosConfig.getTenant(), nacosConfig.getDataId(), nacosConfig.getBizGroup(), query.getPageNo(), query.getPageSize());
        if (Objects.isNull(result)) {
            return PageResult.emptyPageResult(query);
        }
        PageResult<HistoryConfigInfo> pageResult = new PageResult<>();
        pageResult.setPageNum(query.getPageNo());
        pageResult.setPageSize(query.getPageSize());
        pageResult.setTotal(result.getTotalCount());
        pageResult.setPages(result.getPagesAvailable());
        pageResult.setRows(result.getPageItems());
        return pageResult;
    }

    /**
     * 获取历史配置详情
     * @param query
     * @return
     * @throws Exception
     */
    public HistoryConfigInfo findHistoryConfigById(ConfigQuery query) throws Exception {
        HistoryConfigInfo historyConfigInfo = nacosOpenApiClient.queryHistoryById(query.getNamespace(), query.getDataId(), query.getGroup(), String.valueOf(query.getHistoryId()));
        String content = historyConfigInfo.getContent();
        // nacos 配置 掩码替换 敏感信息
        List<AppResource> appResources = listAppResourcesByAppId(query.getAppId());
        String actualContent = reverseReplaceConfigSensitiveInfo(appResources, content);
        historyConfigInfo.setContent(actualContent);
        return historyConfigInfo;
    }


    /**
     * 历史配置回滚
     * @param query
     * @throws Exception
     */
    public void historyConfigRollback(ConfigQuery query) throws Exception {
        HistoryConfigInfo historyConfigInfo = nacosOpenApiClient.queryHistoryById(query.getNamespace(), query.getDataId(), query.getGroup(), String.valueOf(query.getHistoryId()));
        if (Objects.isNull(historyConfigInfo)) {
            throw new BizException(NacosRetCodeEnum.DATA_NOT_EXISTED);
        }
        NacosConfig existedConfig = nacosConfigService.getOne(
                new QueryWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, query.getId())
                        .eq(NacosConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedConfig)) {
            throw new BizException(NacosRetCodeEnum.DATA_NOT_EXISTED);
        }
        NacosConfig updateConfig = new NacosConfig();
        updateConfig.setVersion(existedConfig.getVersion());
        boolean updateResult = nacosConfigService.update(
                updateConfig,
                new UpdateWrapper<NacosConfig>().lambda()
                        .eq(NacosConfig::getId, existedConfig.getId())
                        .eq(NacosConfig::getVersion, existedConfig.getVersion())
        );
        if (!updateResult) {
            throw new BizException(NacosRetCodeEnum.VERSION_ERROR);
        }
        try {
            nacosOpenApiClient.publishConfig(existedConfig.getTenant(), existedConfig.getDataId(), existedConfig.getBizGroup(), historyConfigInfo.getContent(), existedConfig.getType());
        } catch (Exception e) {
            if (e instanceof BizException) {
                throw (BizException) e;
            }
            throw new BizException(NacosRetCodeEnum.SYSTEM_ERROR);

        }
    }

    /**
     * 获取命名空间选择列表
     * @return
     * @throws Exception
     */
    public List<NamespaceSelectVO> getNamespaceSelectList() throws Exception {
        List<NamespaceInfo> namespaceInfos = nacosOpenApiClient.queryNamespaceList();
        return namespaceInfos.stream()
                .map(namespaceInfo -> new NamespaceSelectVO(namespaceInfo.getNamespaceShowName(), namespaceInfo.getNamespace()))
                .collect(Collectors.toList());
    }

    /**
     * 掩码转为敏感信息
     * @param appResourceList
     * @param content
     * @return
     */
    private String replaceConfigSensitiveInfo(List<AppResource> appResourceList, String content) {
        if (CollectionUtils.isEmpty(appResourceList)) {
            return content;
        }
        Map<String, String> maskedMap = maskMapToOriginal(appResourceList);
        Set<String> maskedSet = maskedMap.keySet();
        Collection<String> values = maskedMap.values();
        // 一次性替换所有掩码
        String[] maskedArray = maskedSet.stream()
                .map(masked -> "$" + masked).toArray(String[]::new);
        return StringUtils.replaceEach(content, maskedArray, values.toArray(String[]::new));
    }

    /**
     * 敏感信息转为掩码
     * @param appResourceList
     * @param content
     * @return
     */
    private String reverseReplaceConfigSensitiveInfo(List<AppResource> appResourceList, String content) {
        if (CollectionUtils.isEmpty(appResourceList)) {
            return content;
        }
        Map<String, String> maskedMap = maskMapToOriginal(appResourceList);
        Set<String> maskSet = maskedMap.keySet();
        Collection<String> values = maskedMap.values();
        // 一次性替换所有敏感信息
        // 一次性替换所有掩码
        String[] maskedArray = maskSet.stream()
                .map(masked -> "$" + masked).toArray(String[]::new);
        return StringUtils.replaceEach(content, values.toArray(String[]::new), maskedArray);
    }

    private Map<String, String> maskMapToOriginal(List<AppResource> appResourceList) {
        Map<String, String> result = Maps.newHashMap();
        for (AppResource appResource : appResourceList) {
            if (StringUtils.isNotBlank(appResource.getUrlMask())) {
                result.put(appResource.getUrlMask(), appResource.getUrl());
            }
            if (StringUtils.isNotBlank(appResource.getPortMask()) && Objects.nonNull(appResource.getPort())) {
                result.put(appResource.getPortMask(), appResource.getPort().toString());
            }
            if (StringUtils.isNotBlank(appResource.getUsernameMask())) {
                result.put(appResource.getUsernameMask(), appResource.getUsername());
            }
            if (StringUtils.isNotBlank(appResource.getPasswordMask())) {
                result.put(appResource.getPasswordMask(), appResource.getPassword());
            }
        }
        return result;
    }

    private List<AppResource> listAppResourcesByAppId(Long appId) {
        return appResourceService.list(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getAppId, appId)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
    }
}
