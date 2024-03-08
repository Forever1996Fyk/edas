package com.ahcloud.edas.gateway.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.gateway.core.application.helper.GatewayApiHelper;
import com.ahcloud.edas.gateway.core.application.service.GatewayApiService;
import com.ahcloud.edas.gateway.core.domain.api.form.ApiAddForm;
import com.ahcloud.edas.gateway.core.domain.api.form.ApiUpdateForm;
import com.ahcloud.edas.gateway.core.domain.api.query.ApiQuery;
import com.ahcloud.edas.gateway.core.domain.api.vo.ApiVO;
import com.ahcloud.edas.gateway.core.infrastructure.constant.EnvConstants;
import com.ahcloud.edas.gateway.core.infrastructure.constant.enums.ApiStatusEnum;
import com.ahcloud.edas.gateway.core.infrastructure.constant.enums.GatewayRetCodeEnum;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.enums.ConfigGroupEnum;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.enums.DataEventTypeEnum;
import com.ahcloud.edas.gateway.core.infrastructure.gateway.listener.event.DataChangedEvent;
import com.ahcloud.edas.gateway.core.infrastructure.repository.bean.GatewayApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/4 10:36
 **/
@Slf4j
@Component
public class GatewayApiManager {
    @Resource
    private GatewayApiService gatewayApiService;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 新增api接口
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void createApi(ApiAddForm form) {
        GatewayApi existedGatewayApi = gatewayApiService.getOne(
                new QueryWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getApiCode, form.getApiCode())
                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedGatewayApi)) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_CODE_EXITED);
        }
        // 判断应用实例是否健康
        GatewayApi gatewayApi = GatewayApiHelper.convert(form);
        boolean result = gatewayApiService.save(gatewayApi);
        if (!result) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_ADD_FAILED);
        }
        applicationEventPublisher.publishEvent(
                new DataChangedEvent(
                        ImmutablePair.of(gatewayApi.getApiCode(), 0),
                        EnvConstants.ALL_ENV_LIST,
                        DataEventTypeEnum.CREATE,
                        ConfigGroupEnum.API
                )
        );
    }

    /**
     * 更新api接口
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateApi(ApiUpdateForm form) {
        GatewayApi existedGatewayApi = gatewayApiService.getOne(
                new QueryWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, form.getId())
                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedGatewayApi)) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_NOT_EXITED);
        }
        GatewayApi gatewayApi = GatewayApiHelper.convert(form);
        gatewayApi.setVersion(existedGatewayApi.getVersion());
        boolean result = gatewayApiService.update(
                gatewayApi,
                new UpdateWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, form.getId())
                        .eq(GatewayApi::getVersion, existedGatewayApi.getVersion())
        );
        if (!result) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_UPDATE_FAILED);
        }
        applicationEventPublisher.publishEvent(
                new DataChangedEvent(
                        ImmutablePair.of(gatewayApi.getApiCode(), gatewayApi.getVersion()),
                        EnvConstants.ALL_ENV_LIST,
                        DataEventTypeEnum.UPDATE,
                        ConfigGroupEnum.API
                )
        );
    }

    /**
     * 删除api接口
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteApi(Long id) {
        GatewayApi existedGatewayApi = gatewayApiService.getOne(
                new QueryWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedGatewayApi)) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_NOT_EXITED);
        }
        GatewayApi gatewayApi = new GatewayApi();
        gatewayApi.setDeleted(id);
        gatewayApi.setVersion(existedGatewayApi.getVersion());
        boolean result = gatewayApiService.update(
                gatewayApi,
                new UpdateWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getVersion, existedGatewayApi.getVersion())
        );
        if (!result) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_DELETE_FAILED);
        }
        applicationEventPublisher.publishEvent(
                new DataChangedEvent(
                        ImmutablePair.of(existedGatewayApi.getApiCode(), gatewayApi.getVersion()),
                        EnvConstants.ALL_ENV_LIST,
                        DataEventTypeEnum.DELETE,
                        ConfigGroupEnum.API
                )
        );
    }

    /**
     * 根据id查询api信息
     *
     * @param id
     * @return
     */
    public ApiVO findApiById(Long id) {
        GatewayApi existedGatewayApi = gatewayApiService.getOne(
                new QueryWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedGatewayApi)) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_NOT_EXITED);
        }
        return GatewayApiHelper.convertToVo(existedGatewayApi);
    }

    /**
     * 分页查询api接口
     * @param query
     * @return
     */
    public PageResult<ApiVO> pageApiList(ApiQuery query) {
        PageInfo<GatewayApi> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> gatewayApiService.list(
                                new QueryWrapper<GatewayApi>().lambda()
                                        .orderByDesc(GatewayApi::getModifiedTime)
                                        .eq(
                                                StringUtils.isNotBlank(query.getApiCode()),
                                                GatewayApi::getApiCode,
                                                query.getApiCode()
                                        )
                                        .like(
                                                StringUtils.isNotBlank(query.getName()),
                                                GatewayApi::getApiName,
                                                query.getName()
                                        )
                                        .eq(
                                                StringUtils.isNotBlank(query.getServiceId()),
                                                GatewayApi::getServiceId,
                                                query.getServiceId()
                                        )
                                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return GatewayApiHelper.convertToPageResult(pageInfo);
    }

    /**
     * 刷新接口
     *
     * 加锁操作，防止并发多次刷新
     */
    public void refreshApi() {
        applicationEventPublisher.publishEvent(
                new DataChangedEvent(
                        DataEventTypeEnum.REFRESH.name(),
                        EnvConstants.ALL_ENV_LIST,
                        DataEventTypeEnum.REFRESH,
                        ConfigGroupEnum.API
                )
        );
    }

    /**
     * 下线接口
     * @param id
     */
    public void offlineApi(Long id, String env) {
        operateApi(id, ApiStatusEnum.OFFLINE, env);
    }

    /**
     * 下线接口
     * @param id
     */
    public void onlineApi(Long id, String env) {
        operateApi(id, ApiStatusEnum.ONLINE, env);
    }

    private void operateApi(Long id, ApiStatusEnum apiStatusEnum, String env) {
        GatewayApi existedGatewayApi = gatewayApiService.getOne(
                new QueryWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedGatewayApi)) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_NOT_EXITED);
        }
        GatewayApi updateGatewayApi = new GatewayApi();
        Integer status = apiStatusEnum.getStatus();
        setEnvStatus(env, updateGatewayApi, status);
        updateGatewayApi.setVersion(existedGatewayApi.getVersion());
        boolean updateResult = gatewayApiService.update(
                updateGatewayApi,
                new UpdateWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getVersion, existedGatewayApi.getVersion())
        );
        if (!updateResult) {
            throw new BizException(GatewayRetCodeEnum.VERSION_ERROR);
        }
        applicationEventPublisher.publishEvent(
                new DataChangedEvent(
                        ImmutablePair.of(existedGatewayApi.getApiCode(), existedGatewayApi.getVersion()),
                        Lists.newArrayList(env),
                        DataEventTypeEnum.UPDATE,
                        ConfigGroupEnum.API
                )
        );
    }

    private void setEnvStatus(String env, GatewayApi updateGatewayApi, Integer status) {
        switch (env) {
            case EnvConstants.DEV:
                updateGatewayApi.setDev(status);
                break;
            case EnvConstants.TEST:
                updateGatewayApi.setTest(status);
                break;
            case EnvConstants.SIT:
                updateGatewayApi.setSit(status);
                break;
            case EnvConstants.PRE:
                updateGatewayApi.setPre(status);
                break;
            case EnvConstants.PROD:
                updateGatewayApi.setProd(status);
                break;
            default:
                throw new BizException(GatewayRetCodeEnum.ENV_PARAM_ERROR);
        }
    }

    public void changeApiAuth(Long id, Integer auth) {
        GatewayApi existedGatewayApi = gatewayApiService.getOne(
                new QueryWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedGatewayApi)) {
            throw new BizException(GatewayRetCodeEnum.GATEWAY_API_NOT_EXITED);
        }
        GatewayApi updateGatewayApi = new GatewayApi();
        updateGatewayApi.setAuth(auth);
        updateGatewayApi.setVersion(existedGatewayApi.getVersion());
        boolean updateResult = gatewayApiService.update(
                updateGatewayApi,
                new UpdateWrapper<GatewayApi>().lambda()
                        .eq(GatewayApi::getId, id)
                        .eq(GatewayApi::getVersion, existedGatewayApi.getVersion())
        );
        if (!updateResult) {
            throw new BizException(GatewayRetCodeEnum.VERSION_ERROR);
        }
        applicationEventPublisher.publishEvent(
                new DataChangedEvent(
                        ImmutablePair.of(existedGatewayApi.getApiCode(), existedGatewayApi.getVersion()),
                        EnvConstants.ALL_ENV_LIST,
                        DataEventTypeEnum.UPDATE,
                        ConfigGroupEnum.API
                )
        );
    }
}
