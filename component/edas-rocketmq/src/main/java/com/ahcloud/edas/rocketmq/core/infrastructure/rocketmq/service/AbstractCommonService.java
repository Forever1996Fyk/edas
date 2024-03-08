package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.service;

import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.tools.admin.MQAdminExt;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/29 16:32
 **/
public abstract class AbstractCommonService {

    @Resource
    protected MQAdminExt mqAdminExt;

    /**
     * 更新brokerName
     * @param clusterAddrTable
     * @param clusterNameList
     * @param brokerNameList
     * @return
     */
    protected final Set<String> changeToBrokerNameSet(Map<String, Set<String>> clusterAddrTable,
                                                      List<String> clusterNameList, List<String> brokerNameList) {
        Set<String> finalBrokerNameList = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(clusterNameList)) {
            try {
                for (String clusterName : clusterNameList) {
                    finalBrokerNameList.addAll(clusterAddrTable.get(clusterName));
                }
            }
            catch (Exception e) {
                Throwables.throwIfUnchecked(e);
                throw new RuntimeException(e);
            }
        }
        if (CollectionUtils.isNotEmpty(brokerNameList)) {
            finalBrokerNameList.addAll(brokerNameList);
        }
        return finalBrokerNameList;
    }
}
