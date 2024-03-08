package com.ahcloud.edas.admin.biz.application.manager;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.util.PageUtils;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.enums.ResourceTypeEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.admin.biz.application.service.AppResourceService;
import com.ahcloud.edas.admin.biz.domain.resource.ResourceConfig;
import com.ahcloud.edas.admin.biz.domain.resource.form.ResourceAddForm;
import com.ahcloud.edas.admin.biz.domain.resource.form.ResourceUpdateForm;
import com.ahcloud.edas.admin.biz.domain.resource.query.ResourceQuery;
import com.ahcloud.edas.admin.biz.domain.resource.vo.ResourceDetailsVO;
import com.ahcloud.edas.admin.biz.domain.resource.vo.ResourceVO;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.AppResource;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 16:04
 **/
@Slf4j
@Component
public class AppResourceManager {
    @Resource
    private AppResourceService appResourceService;

    /**
     * 新增资源
     * @param form
     */
    public void addResource(ResourceAddForm form) {
        Long appId = form.getAppId();
        AppResource existedResource = appResourceService.getOne(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getAppId, appId)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
        AppResource appResource = new AppResource();
        appResource.setAppId(appId);
        appResource.setResourceConfig(JsonUtils.toJsonString(form.getResourceConfig()));
        appResource.setName(form.getName());
        appResource.setType(form.getType());
        appResource.setUrl(form.getUrl());
        appResource.setPort(form.getPort());
        appResource.setUsername(form.getUsername());
        appResource.setPassword(form.getPassword());

        appResource.setUrlMask(RandomUtil.randomString(6));
        appResource.setPortMask(RandomUtil.randomString(6));
        appResource.setUsernameMask(RandomUtil.randomString(6));
        appResource.setPasswordMask(RandomUtil.randomString(6));
        appResource.setCreator(UserUtils.getUserNameBySession());
        appResource.setModifier(UserUtils.getUserNameBySession());
        appResourceService.save(appResource);
    }

    /**
     * 修改资源
     * @param form
     */
    public void updateResource(ResourceUpdateForm form) {
        Long appId = form.getAppId();
        AppResource existedResource = appResourceService.getOne(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getAppId, appId)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedResource)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        AppResource updateAppResource = new AppResource();
        updateAppResource.setId(existedResource.getId());
        updateAppResource.setAppId(appId);
        updateAppResource.setResourceConfig(JsonUtils.toJsonString(form.getResourceConfig()));
        updateAppResource.setName(form.getName());
        updateAppResource.setType(form.getType());
        updateAppResource.setUrl(form.getUrl());
        updateAppResource.setPort(form.getPort());
        updateAppResource.setUsername(form.getUsername());
        updateAppResource.setPassword(form.getPassword());
        updateAppResource.setModifier(UserUtils.getUserNameBySession());
        appResourceService.updateById(updateAppResource);
    }

    /**
     * 删除资源
     * @param id
     */
    public void deleteResourceById(Long id) {
        AppResource existedResource = appResourceService.getOne(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getId, id)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedResource)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        AppResource delete = new AppResource();
        delete.setId(id);
        delete.setDeleted(id);
        delete.setModifier(UserUtils.getUserNameBySession());
        appResourceService.updateById(delete);
    }

    /**
     * 根据id查询资源信息
     * @param id
     * @return
     */
    public ResourceVO findResourceById(Long id) {
        AppResource existedResource = appResourceService.getOne(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getId, id)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedResource)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        return this.convert(existedResource);
    }

    /**
     * 根据id查询资源信息
     * @param id
     * @return
     */
    public ResourceDetailsVO findResourceDetailById(Long id) {
        AppResource existedResource = appResourceService.getOne(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getId, id)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedResource)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        ResourceDetailsVO resourceDetailsVO = Convert.convert(ResourceDetailsVO.class, existedResource);
        if (StringUtils.isNotBlank(existedResource.getResourceConfig())) {
            ResourceConfig resourceConfig = JsonUtils.stringToBean(existedResource.getResourceConfig(), ResourceConfig.class);
            resourceDetailsVO.setResourceConfig(resourceConfig);
        }
        return resourceDetailsVO;
    }

    /**
     * 分页查询资源列表
     * @param query
     * @return
     */
    public PageResult<ResourceVO> pageResourceList(ResourceQuery query) {
        PageInfo<AppResource> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> appResourceService.list(
                                new QueryWrapper<AppResource>().lambda()
                                        .eq(AppResource::getAppId, query.getAppId())
                                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return PageUtils.pageInfoConvertToPageResult(pageInfo, this.convertToList(pageInfo.getList()));
    }

    /**
     * 获取app资源集合
     * @param appId
     * @return
     */
    public List<AppResource> listAppResourcesByAppId(Long appId) {
        return appResourceService.list(
                new QueryWrapper<AppResource>().lambda()
                        .eq(AppResource::getAppId, appId)
                        .eq(AppResource::getDeleted, DeletedEnum.NO.value)
        );
    }

    private List<ResourceVO> convertToList(List<AppResource> resources) {
        return resources.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private ResourceVO convert(AppResource appResource) {
        return ResourceVO.builder()
                .id(appResource.getId())
                .type(appResource.getType())
                .typeName(ResourceTypeEnum.getByType(appResource.getType()).getDesc())
                .env(appResource.getEnv())
                .appCode(appResource.getAppCode())
                .appId(appResource.getAppId())
                .name(appResource.getName())
                .resourceSpec(
                        StringUtils.isBlank(appResource.getResourceConfig()) ?
                                "" :
                                getSpec(
                                        Objects.requireNonNull(JsonUtils.stringToBean(appResource.getResourceConfig(), ResourceConfig.class))
                                )

                )
                .passwordMask(appResource.getPasswordMask())
                .urlMask(appResource.getUrlMask())
                .portMask(appResource.getPortMask())
                .usernameMask(appResource.getUsernameMask())
                .build();
    }

    private String getSpec(ResourceConfig resourceConfig) {
        Integer core = resourceConfig.getCore();
        Integer ram = resourceConfig.getRam();
        return String.format("%s核%sG", core, ram);
    }
}
