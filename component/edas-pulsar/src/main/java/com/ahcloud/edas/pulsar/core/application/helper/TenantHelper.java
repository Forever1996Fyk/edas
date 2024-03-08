package com.ahcloud.edas.pulsar.core.application.helper;

import com.ahcloud.edas.pulsar.core.domain.tenant.form.TenantAddForm;
import com.ahcloud.edas.pulsar.core.domain.tenant.form.TenantUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantVO;
import com.ahcloud.edas.pulsar.core.infrastructure.repository.bean.PulsarTenant;
import com.ahcloud.edas.uaa.client.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.common.policies.data.TenantInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/19 09:29
 **/
public class TenantHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarTenant convert(TenantAddForm form) {
        PulsarTenant pulsarTenant = new PulsarTenant();
        pulsarTenant.setTenantName(form.getTenantName());
        pulsarTenant.setAdminRoles(StringUtils.join(form.getAdminRoles(), ","));
        pulsarTenant.setAllowedClusters(StringUtils.join(form.getAllowedClusters(), ","));
        pulsarTenant.setType(form.getType());
        pulsarTenant.setCreator(UserUtils.getUserNameBySession());
        pulsarTenant.setModifier(UserUtils.getUserNameBySession());
        return pulsarTenant;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static PulsarTenant convert(TenantUpdateForm form) {
        PulsarTenant pulsarTenant = new PulsarTenant();
        pulsarTenant.setAdminRoles(StringUtils.join(form.getAdminRoles(), ","));
        pulsarTenant.setAllowedClusters(StringUtils.join(form.getAllowedClusters(), ","));
        pulsarTenant.setModifier(UserUtils.getUserNameBySession());
        return pulsarTenant;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static TenantInfo convertToInfo(TenantAddForm form) {
        return TenantInfo.builder()
                .allowedClusters(form.getAllowedClusters())
                .adminRoles(form.getAdminRoles())
                .build();
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static TenantInfo convertToInfo(TenantUpdateForm form) {
        return TenantInfo.builder()
                .allowedClusters(form.getAllowedClusters())
                .adminRoles(form.getAdminRoles())
                .build();
    }

    /**
     * 数据转换
     * @param list
     * @return
     */
    public static List<TenantVO> convertToList(List<PulsarTenant> list) {
        return list.stream().map(pulsarTenant ->
                        TenantVO.builder()
                                .id(pulsarTenant.getId())
                                .tenantName(pulsarTenant.getTenantName())
                                .adminRoles(pulsarTenant.getAdminRoles())
                                .allowedClusters(pulsarTenant.getAllowedClusters())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
