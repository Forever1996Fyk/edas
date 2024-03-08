package com.ahcloud.edas.powerjob.biz.application.helper;

import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.powerjob.biz.domain.instance.query.JobInstanceQuery;
import com.ahcloud.edas.powerjob.biz.domain.instance.vo.JobInstanceVO;
import tech.powerjob.common.PowerQuery;
import tech.powerjob.common.enums.InstanceStatus;
import tech.powerjob.common.request.query.InstanceQuery;
import tech.powerjob.common.response.InstanceInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 10:11
 **/
public class PowerJobInstanceHelper {

    /**
     * 数据转换
     * @param query
     * @return
     */
    public static InstanceQuery convert(JobInstanceQuery query) {
        InstanceQuery powerQuery = new InstanceQuery();
        powerQuery.setAppIdEq(query.getPowerJobAppId());
        powerQuery.setJobIdEq(query.getJobId());
        powerQuery.setPageNo(query.getPageNo());
        powerQuery.setPageSize(query.getPageSize());
        return powerQuery;
    }

    /**
     * 数据转换
     * @param instanceInfoDTOList
     * @return
     */
    public static List<JobInstanceVO> convertToVOList(List<InstanceInfoDTO> instanceInfoDTOList) {
        return instanceInfoDTOList.stream()
                .map(PowerJobInstanceHelper::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 数据转换
     * @param instanceInfoDTO
     * @return
     */
    public static JobInstanceVO convertToVO(InstanceInfoDTO instanceInfoDTO) {
        return JobInstanceVO.builder()
                .jobId(instanceInfoDTO.getJobId())
                .instanceId(instanceInfoDTO.getInstanceId())
                .wfInstanceId(instanceInfoDTO.getWfInstanceId())
                .powerJobAppId(instanceInfoDTO.getAppId())
                .actualTriggerTime(DateUtils.parse(instanceInfoDTO.getActualTriggerTime()))
                .instanceParams(instanceInfoDTO.getInstanceParams())
                .jobParams(instanceInfoDTO.getJobParams())
                .expectedTriggerTime(DateUtils.parse(instanceInfoDTO.getExpectedTriggerTime()))
                .finishedTime(DateUtils.parse(instanceInfoDTO.getFinishedTime()))
                .result(instanceInfoDTO.getResult())
                .status(instanceInfoDTO.getStatus())
                .statusName(InstanceStatus.of(instanceInfoDTO.getStatus()).getDes())
                .type(instanceInfoDTO.getType())
                .runningTimes(instanceInfoDTO.getRunningTimes())
                .taskTrackerAddress(instanceInfoDTO.getTaskTrackerAddress())
                .build();
    }
}
