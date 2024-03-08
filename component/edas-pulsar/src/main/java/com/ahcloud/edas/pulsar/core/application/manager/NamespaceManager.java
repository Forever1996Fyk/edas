package com.ahcloud.edas.pulsar.core.application.manager;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.pulsar.core.application.helper.NamespaceHelper;
import com.ahcloud.edas.pulsar.core.application.helper.PoliciesHelper;
import com.ahcloud.edas.pulsar.core.application.helper.TopicHelper;
import com.ahcloud.edas.pulsar.core.application.service.PulsarNamespaceService;
import com.ahcloud.edas.pulsar.core.application.service.PulsarTenantService;
import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import com.ahcloud.edas.pulsar.core.domain.namespace.form.NamespaceAddForm;
import com.ahcloud.edas.pulsar.core.domain.namespace.form.NamespaceUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.namespace.query.NamespaceQuery;
import com.ahcloud.edas.pulsar.core.domain.namespace.vo.NamespaceDetailVO;
import com.ahcloud.edas.pulsar.core.domain.namespace.vo.NamespaceVO;
import com.ahcloud.edas.pulsar.core.infrastructure.constant.enums.PulsarRetCodeEnum;
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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.pulsar.client.admin.Namespaces;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.common.policies.data.Policies;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 10:40
 **/
@Slf4j
@Component
public class NamespaceManager {
    @Resource
    private PulsarStatsService pulsarStatsService;
    @Resource
    private PulsarTenantService pulsarTenantService;
    @Resource
    private PulsarAdminService pulsarAdminService;
    @Resource
    private PulsarNamespaceService pulsarNamespaceService;

    /**
     * 新增命名空间
     * @param form
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void addNamespace(NamespaceAddForm form) throws PulsarAdminException {
        String namespaceName = form.getNamespaceName();
        PulsarNamespace existedPulsarNamespace = pulsarNamespaceService.getOne(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getNamespaceName, namespaceName)
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.nonNull(existedPulsarNamespace)) {
            throw new BizException(PulsarRetCodeEnum.DATA_EXISTED);
        }
        PulsarTenant pulsarTenant = pulsarTenantService.getById(form.getTenantId());
        if (Objects.isNull(pulsarTenant)) {
            throw new BizException(PulsarRetCodeEnum.TENANT_NOT_EXISTED);
        }
        PulsarNamespace pulsarNamespace = NamespaceHelper.convert(form);
        pulsarNamespace.setTenantName(pulsarTenant.getTenantName());
        pulsarNamespaceService.save(pulsarNamespace);
        Namespaces namespaces = pulsarAdminService.namespaces();
        ImmutablePair<String, Policies> pair = NamespaceHelper.buildNamespace(pulsarNamespace, form.getPolicyDTO());
        namespaces.createNamespace(pair.getLeft(), pair.getRight());
    }

    /**
     * 更新命名空间
     * @param form
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateNamespace(NamespaceUpdateForm form) throws PulsarAdminException {
        PulsarNamespace existedPulsarNamespace = pulsarNamespaceService.getOne(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getId, form.getId())
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarNamespace)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarNamespace pulsarNamespace = NamespaceHelper.convert(form);
        pulsarNamespace.setVersion(existedPulsarNamespace.getVersion());
        boolean update = pulsarNamespaceService.update(
                pulsarNamespace,
                new UpdateWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getId, form.getId())
                        .eq(PulsarNamespace::getVersion, existedPulsarNamespace.getVersion())
        );
        if (!update) {
            throw new BizException(PulsarRetCodeEnum.VERSION_ERROR);
        }
        PolicyDTO newPolicyDTO = form.getPolicyDTO();
        PolicyDTO policyDTO = JsonUtils.stringToBean(existedPulsarNamespace.getPoliciesJson(), PolicyDTO.class);
        if (Objects.isNull(policyDTO)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        Namespaces namespaces = pulsarAdminService.namespaces();
        if (!Objects.equals(newPolicyDTO.getNamespaceMessageTTL(), policyDTO.getNamespaceMessageTTL())) {
            namespaces.setNamespaceMessageTTL(
                    NamespaceHelper.generateNamespace(existedPulsarNamespace.getTenantName(), existedPulsarNamespace.getNamespaceName()),
                    PoliciesHelper.parseMessageTtlSeconds(newPolicyDTO)
            );
        }
        if (!Objects.equals(newPolicyDTO.getRetentionPolicies(), policyDTO.getRetentionPolicies())) {
            namespaces.setRetention(
                    NamespaceHelper.generateNamespace(existedPulsarNamespace.getTenantName(), existedPulsarNamespace.getNamespaceName()),
                    PoliciesHelper.buildRetention(newPolicyDTO)
            );
        }
        if (!Objects.equals(newPolicyDTO.getAutoCreateSubscription(), policyDTO.getAutoCreateSubscription())) {
            namespaces.setAutoSubscriptionCreation(
                    NamespaceHelper.generateNamespace(existedPulsarNamespace.getTenantName(), existedPulsarNamespace.getNamespaceName()),
                    PoliciesHelper.buildAutoSubscriptionCreation(newPolicyDTO)
            );
        }
    }

    /**
     * 删除命名空间
     * @param id
     * @throws PulsarAdminException
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteNamespace(Long id) throws PulsarAdminException {
        PulsarNamespace existedPulsarNamespace = pulsarNamespaceService.getOne(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getId, id)
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarNamespace)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        PulsarNamespace deletePulsarNamespace = new PulsarNamespace();
        deletePulsarNamespace.setDeleted(id);
        deletePulsarNamespace.setVersion(existedPulsarNamespace.getVersion());
        pulsarNamespaceService.update(
                deletePulsarNamespace,
                new UpdateWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getId, id)
                        .eq(PulsarNamespace::getVersion, existedPulsarNamespace.getVersion())
        );
        Namespaces namespaces = pulsarAdminService.namespaces();
        namespaces.deleteNamespace(NamespaceHelper.generateNamespace(existedPulsarNamespace.getTenantName(), existedPulsarNamespace.getNamespaceName()));
    }

    /**
     * 根据id查询命名空间详情
     * @param id
     * @return
     */
    public NamespaceDetailVO findById(Long id) {
        PulsarNamespace existedPulsarNamespace = pulsarNamespaceService.getOne(
                new QueryWrapper<PulsarNamespace>().lambda()
                        .eq(PulsarNamespace::getId, id)
                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedPulsarNamespace)) {
            throw new BizException(PulsarRetCodeEnum.DATA_NOT_EXISTED);
        }
        NamespaceDetailVO.NamespaceDetailVOBuilder builder = NamespaceDetailVO.builder()
                .id(existedPulsarNamespace.getId())
                .tenantId(existedPulsarNamespace.getTenantId())
                .tenantName(existedPulsarNamespace.getTenantName())
                .namespaceName(existedPulsarNamespace.getNamespaceName())
                .description(existedPulsarNamespace.getDescription())
                .policyDTO(JsonUtils.stringToBean(existedPulsarNamespace.getPoliciesJson(), PolicyDTO.class));
        Map<Long, PulsarTopicStats> topicStatsByNamespace = pulsarStatsService.getTopicStatsByNamespace(Lists.newArrayList(existedPulsarNamespace.getId()));
        if (CollectionUtils.isEmpty(topicStatsByNamespace)) {
            return builder.build();
        }
        PulsarTopicStats pulsarTopicStats = topicStatsByNamespace.get(id);
        if (Objects.isNull(pulsarTopicStats)) {
            return builder.build();
        }
        return builder
                .statsDTO(TopicHelper.convert(pulsarTopicStats))
                .build();
    }

    /**
     * 分页查询命名空间列表
     * @param query
     * @return
     */
    public PageResult<NamespaceVO> pageNamespaceList(NamespaceQuery query) {
        if (query.getTenantId() == null) {
            throw new BizException(PulsarRetCodeEnum.PARAM_ILLEGAL);
        }
        PageInfo<PulsarNamespace> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> pulsarNamespaceService.list(
                                new QueryWrapper<PulsarNamespace>().lambda()
                                        .eq(PulsarNamespace::getTenantId, query.getTenantId())
                                        .like(
                                                StringUtils.isNotBlank(query.getNamespaceName()),
                                                PulsarNamespace::getTenantName,
                                                query.getNamespaceName()
                                        )
                                        .eq(PulsarNamespace::getDeleted, DeletedEnum.NO.value)
                        )
                );
        return PageUtils.pageInfoConvertToPageResult(pageInfo, NamespaceHelper.convertToList(pageInfo.getList()));
    }
}
