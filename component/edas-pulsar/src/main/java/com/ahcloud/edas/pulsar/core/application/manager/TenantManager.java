package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.pulsar.core.application.helper.TenantHelper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarNamespaceService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTenantService;
import com.ahcloud.edas.pulsar.core.domain.common.StatsDTO;
import com.ahcloud.edas.pulsar.core.domain.tenant.form.TenantAddForm;
import com.ahcloud.edas.pulsar.core.domain.tenant.form.TenantUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.tenant.query.TenantQuery;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantDetailVO;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantSelectVO;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantVO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.TenantTypeEnum;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.admin.PulsarAdminService;
import com.ahcloud.edas.pulsar.core.infrastructure.pulsar.stats.PulsarStatsService;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarNamespace;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTenant;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTopicStats;
import com.ahcloud.edas.pulsar.core.infrastructure.util.PageUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.admin.Namespaces;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.apache.pulsar.shade.com.google.common.base.Throwables;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 09:19
 **/
@Slf4j
@Component
public class TenantManager {
    @Resource
    private PulsarStatsService pulsarStatsService;
    @Resource
    private PulsarAdminService pulsarAdminService;
    @Resource
    private PulsarTenantService pulsarTenantService;
    @Resource
    private PulsarNamespaceService pulsarNamespaceService;

    /**
     * 新增租户
     * @param form
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void addTenant(TenantAddForm form) throws PulsarAdminException {
        String tenantName = form.getTenantName();
        PulsarTenant existedPulsarTenant = pulsarTenantService.getOne(
                new QueryWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getTenantName, tenantName)
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedPulsarTenant)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        PulsarTenant pulsarTenant = TenantHelper.convert(form);
        pulsarTenantService.save(pulsarTenant);
        Tenants tenants = pulsarAdminService.tenants();
        tenants.createTenant(form.getTenantName(), TenantHelper.convertToInfo(form));
    }

    /**
     * 更新租户
     * @param form
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTenant(TenantUpdateForm form) throws PulsarAdminException {
        PulsarTenant existedPulsarTenant = pulsarTenantService.getOne(
                new QueryWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getId, form.getId())
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarTenant)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarTenant pulsarTenant = TenantHelper.convert(form);
        pulsarTenant.setVersion(existedPulsarTenant.getVersion());
        boolean update = pulsarTenantService.update(
                pulsarTenant,
                new UpdateWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getId, form.getId())
                        .eq(PulsarTenant::getVersion, existedPulsarTenant.getVersion())
        );
        if (!update) {
            throw new BizException(PulsarRetCodeEnum.VERSION_ERROR);
        }
        Tenants tenants = pulsarAdminService.tenants();
        tenants.updateTenant(existedPulsarTenant.getTenantName(), TenantHelper.convertToInfo(form));
    }

    /**
     * 删除租户
     * @param id
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTenant(Long id) throws PulsarAdminException {
        PulsarTenant existedPulsarTenant = pulsarTenantService.getOne(
                new QueryWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getId, id)
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarTenant)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarTenant deletePulsarTenant = new PulsarTenant();
        deletePulsarTenant.setDeleted(id);
        deletePulsarTenant.setVersion(existedPulsarTenant.getVersion());
        pulsarTenantService.update(
                deletePulsarTenant,
                new UpdateWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getId, id)
                        .eq(PulsarTenant::getVersion, existedPulsarTenant.getVersion())
        );
        Tenants tenants = pulsarAdminService.tenants();
        tenants.deleteTenant(existedPulsarTenant.getTenantName());
    }

    /**
     * 根据id查询租户详情
     * @param id
     * @return
     */
    public TenantDetailVO findById(Long id) {
        PulsarTenant existedPulsarTenant = pulsarTenantService.getOne(
                new QueryWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getId, id)
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarTenant)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        int count = pulsarNamespaceService.count(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getTenantId, existedPulsarTenant.getId())
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        TenantDetailVO.TenantDetailVOBuilder builder = TenantDetailVO.builder()
                .id(existedPulsarTenant.getId())
                .namespaceNum(count)
                .tenantName(existedPulsarTenant.getTenantName())
                .adminRoles(
                        Sets.newHashSet(
                                Arrays.asList(StringUtils.split(existedPulsarTenant.getAdminRoles(), ","))
                        )
                )
                .allowedClusters(
                        Sets.newHashSet(
                                Arrays.asList(StringUtils.split(existedPulsarTenant.getAdminRoles(), ","))
                        )
                );
        Map<Long, PulsarTopicStats> topicStatsByTenant = pulsarStatsService.getTopicStatsByTenant(Lists.newArrayList(existedPulsarTenant.getId()));
        if (CollectionUtils.isEmpty(topicStatsByTenant)) {
            return builder.build();
        }
        PulsarTopicStats pulsarTopicStats = topicStatsByTenant.get(existedPulsarTenant.getId());
        if (Objects.isNull(pulsarTopicStats)) {
            return builder.build();
        }
        return builder
                .storageSize(pulsarTopicStats.getStorageSize())
                .msgRateIn(String.valueOf(pulsarTopicStats.getMsgRateIn()))
                .msgRateOut(String.valueOf(pulsarTopicStats.getMsgRateOut()))
                .msgThroughPutIn(String.valueOf(pulsarTopicStats.getMsgThroughputIn()))
                .msgThroughPutOut(String.valueOf(pulsarTopicStats.getMsgThroughputOut()))
                .build();
    }

    /**
     * 分页查询租户列表
     * @param query
     * @return
     */
    public PageResult<TenantVO> pageTenantList(TenantQuery query) {
        PageInfo<PulsarTenant> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> pulsarTenantService.list(
                                new QueryWrapper<PulsarTenant>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getTenantName()),
                                                PulsarTenant::getTenantName,
                                                query.getTenantName()
                                        )
                                        .eq(
                                                query.getType() != null,
                                                PulsarTenant::getType,
                                                query.getType()
                                        )
                                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return PageUtils.pageInfoConvertToPageResult(pageInfo, TenantHelper.convertToList(pageInfo.getList()));
    }

    /**
     * 获取租户选择列表
     *
     * 这里只查询业务类型
     * @return
     */
    public List<TenantSelectVO> getTenantSelectList() {
        List<PulsarTenant> pulsarTenantList = pulsarTenantService.list(
                new QueryWrapper<PulsarTenant>().lambda()
                        .eq(PulsarTenant::getType, TenantTypeEnum.BIZ.getType())
                        .eq(PulsarTenant::getDeleted, DeletedEnum.NO.value)
        );
        return pulsarTenantList.stream()
                .map(pulsarTenant -> TenantSelectVO.builder()
                        .name(pulsarTenant.getTenantName())
                        .id(pulsarTenant.getId())
                        .build())
                .collect(Collectors.toList());

    }
}
