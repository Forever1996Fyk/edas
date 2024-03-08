package com.ahcloud.edas.pulsar.core.application.helper;

import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.pulsar.core.domain.namespace.dto.PolicyDTO;
import com.ahcloud.edas.pulsar.core.domain.namespace.form.NamespaceAddForm;
import com.ahcloud.edas.pulsar.core.domain.namespace.form.NamespaceUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.namespace.vo.NamespaceVO;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarNamespace;
import com.ahcloud.edas.uaa.client.UserUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.pulsar.common.policies.data.Policies;
import org.apache.pulsar.common.policies.data.RetentionPolicies;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 10:42
 **/
public class NamespaceHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarNamespace convert(NamespaceAddForm form) {
        PulsarNamespace pulsarNamespace = new PulsarNamespace();
        pulsarNamespace.setNamespaceName(form.getNamespaceName());
        pulsarNamespace.setTenantId(form.getTenantId());
        pulsarNamespace.setPoliciesJson(JsonUtils.toJsonString(form.getPolicyDTO()));
        pulsarNamespace.setDescription(form.getDescription());
        pulsarNamespace.setCreator(UserUtils.getUserNameBySession());
        pulsarNamespace.setModifier(UserUtils.getUserNameBySession());
        return pulsarNamespace;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarNamespace convert(NamespaceUpdateForm form) {
        PulsarNamespace pulsarNamespace = new PulsarNamespace();
        pulsarNamespace.setPoliciesJson(JsonUtils.toJsonString(form.getPolicyDTO()));
        pulsarNamespace.setDescription(form.getDescription());
        pulsarNamespace.setModifier(UserUtils.getUserNameBySession());
        return pulsarNamespace;
    }

    /**
     * 构建namespace
     * @param pulsarNamespace
     * @param policyDTO
     * @return
     */
    public static ImmutablePair<String, Policies> buildNamespace(PulsarNamespace pulsarNamespace, PolicyDTO policyDTO) {
        return ImmutablePair.of(
                generateNamespace(pulsarNamespace.getTenantName(), pulsarNamespace.getNamespaceName()),
                PoliciesHelper.buildPolicies(policyDTO)
        );
    }


    /**
     * 生成命名空间名称
     * @param tenant
     * @param namespace
     * @return
     */
    public static String generateNamespace(String tenant, String namespace) {
        return tenant + "/" + namespace;
    }

    /**
     * 数据转换
     * @param list
     * @return
     */
    public static List<NamespaceVO> convertToList(List<PulsarNamespace> list) {
        return list.stream()
                .map(pulsarNamespace ->
                        NamespaceVO.builder()
                                .id(pulsarNamespace.getId())
                                .namespaceName(pulsarNamespace.getNamespaceName())
                                .tenantId(pulsarNamespace.getTenantId())
                                .tenantName(pulsarNamespace.getTenantName())
                                .description(pulsarNamespace.getDescription())
                                .policyDTO(JsonUtils.stringToBean(pulsarNamespace.getPoliciesJson(), PolicyDTO.class))
                                .build()
                )
                .collect(Collectors.toList());
    }
}
