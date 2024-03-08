package com.ahcloud.edas.powerjob.biz.application.helper;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.powerjob.biz.domain.job.form.JobAddForm;
import com.ahcloud.edas.powerjob.biz.domain.job.form.JobUpdateForm;
import com.ahcloud.edas.powerjob.biz.domain.job.query.JobQuery;
import com.ahcloud.edas.powerjob.biz.domain.job.vo.JobInfoVO;
import com.ahcloud.edas.powerjob.biz.domain.job.vo.JobVO;
import com.google.common.collect.Lists;
import tech.powerjob.common.enums.ExecuteType;
import tech.powerjob.common.enums.LogLevel;
import tech.powerjob.common.enums.LogType;
import tech.powerjob.common.enums.ProcessorType;
import tech.powerjob.common.enums.TimeExpressionType;
import tech.powerjob.common.model.LogConfig;
import tech.powerjob.common.request.http.SaveJobInfoRequest;
import tech.powerjob.common.request.query.JobInfoQuery;
import tech.powerjob.common.response.JobInfoDTO;

import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:53
 **/
public class PowerJobHelper {

    private final static Integer DEFAULT_MAX_INSTANCE_NUM = 1;

    /**
     * 数据转换
     *
     * @param powerJobAppId
     * @param form
     * @return
     */
    public static SaveJobInfoRequest convert(Long powerJobAppId, JobAddForm form) {
        SaveJobInfoRequest request = new SaveJobInfoRequest();
        request.setAppId(powerJobAppId);
        request.setJobName(form.getJobName());
        request.setJobDescription(form.getJobDescription());
        request.setJobParams(form.getJobParams());
        request.setTimeExpressionType(TimeExpressionType.of(form.getTimeExpressionType()));
        request.setTimeExpression(form.getTimeExpression());
        request.setExecuteType(ExecuteType.of(form.getExecuteType()));
        request.setProcessorType(ProcessorType.of(form.getProcessorType()));
        request.setProcessorInfo(form.getProcessorInfo());
        request.setMaxInstanceNum(DEFAULT_MAX_INSTANCE_NUM);
        request.setLogConfig(buildLogConfig());
        return request;
    }

    /**
     * 构建日志配置
     * @return
     */
    private static LogConfig buildLogConfig() {
        LogConfig logConfig = new LogConfig();
        logConfig.setLevel(LogLevel.INFO.getV());
        logConfig.setType(LogType.LOCAL.getV());
        return logConfig;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public static SaveJobInfoRequest convert(JobUpdateForm form) {
        SaveJobInfoRequest request = new SaveJobInfoRequest();
        request.setId(form.getId());
        request.setAppId(form.getAppId());
        request.setJobName(form.getJobName());
        request.setJobDescription(form.getJobDescription());
        request.setJobParams(form.getJobParams());
        request.setTimeExpressionType(TimeExpressionType.of(form.getTimeExpressionType()));
        request.setTimeExpression(form.getTimeExpression());
        request.setExecuteType(ExecuteType.of(form.getExecuteType()));
        request.setProcessorType(ProcessorType.of(form.getProcessorType()));
        request.setProcessorInfo(form.getProcessorInfo());
        request.setMaxInstanceNum(DEFAULT_MAX_INSTANCE_NUM);
        request.setLogConfig(buildLogConfig());
        return request;
    }

    /**
     * 数据转换
     * @param jobInfoDTO
     * @return
     */
    public static JobInfoVO convertToInfoVO(JobInfoDTO jobInfoDTO) {
        return JobInfoVO.builder()
                .id(jobInfoDTO.getId())
                .appId(jobInfoDTO.getAppId())
                .jobName(jobInfoDTO.getJobName())
                .timeExpressionType(jobInfoDTO.getTimeExpressionType())
                .timeExpression(jobInfoDTO.getTimeExpression())
                .processorInfo(jobInfoDTO.getProcessorInfo())
                .executeType(jobInfoDTO.getExecuteType())
                .processorType(jobInfoDTO.getProcessorType())
                .jobParams(jobInfoDTO.getJobParams())
                .jobDescription(jobInfoDTO.getJobDescription())
                .build();
    }

    /**
     * 数据转换
     * @param jobQuery
     * @return
     */
    public static JobInfoQuery convertToQuery(JobQuery jobQuery) {
        JobInfoQuery jobInfoQuery = new JobInfoQuery();
        jobInfoQuery.setAppIdEq(jobQuery.getAppId());
        jobInfoQuery.setJobNameLike(jobQuery.getJobName());
        jobInfoQuery.setStatusIn(Lists.newArrayList(1, 2));
        return jobInfoQuery;
    }

    /**
     * 数据转换
     * @param jobInfoDTOList
     * @return
     */
    public static List<JobVO> convertToVOList(List<JobInfoDTO> jobInfoDTOList) {
        return CollectionUtils.convertList(jobInfoDTOList, PowerJobHelper::convertToVO);
    }

    /**
     * 数据转换
     * @param jobInfoDTO
     * @return
     */
    public static JobVO convertToVO(JobInfoDTO jobInfoDTO) {
        return JobVO.builder()
                .id(jobInfoDTO.getId())
                .jobName(jobInfoDTO.getJobName())
                .appId(jobInfoDTO.getAppId())
                .executeType(jobInfoDTO.getExecuteType())
                .executeTypeName(ExecuteType.of(jobInfoDTO.getExecuteType()).getDes())
                .instanceTimeLimit(jobInfoDTO.getInstanceTimeLimit())
                .nextTriggerTime(DateUtils.parse(jobInfoDTO.getNextTriggerTime()))
                .processorType(jobInfoDTO.getProcessorType())
                .processorTypeName(ProcessorType.of(jobInfoDTO.getProcessorType()).getDes())
                .status(jobInfoDTO.getStatus())
                .timeExpression(jobInfoDTO.getTimeExpression())
                .timeExpressionType(jobInfoDTO.getTimeExpressionType())
                .timeExpressionTypeName(TimeExpressionType.of(jobInfoDTO.getTimeExpressionType()).name())
                .build();
    }
}
