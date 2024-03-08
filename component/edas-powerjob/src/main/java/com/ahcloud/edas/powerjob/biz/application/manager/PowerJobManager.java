package com.ahcloud.edas.powerjob.biz.application.manager;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.powerjob.biz.application.helper.PowerJobHelper;
import com.ahcloud.edas.powerjob.biz.domain.job.form.JobAddForm;
import com.ahcloud.edas.powerjob.biz.domain.job.form.JobUpdateForm;
import com.ahcloud.edas.powerjob.biz.domain.job.form.OperateJobForm;
import com.ahcloud.edas.powerjob.biz.domain.job.query.JobQuery;
import com.ahcloud.edas.powerjob.biz.domain.job.vo.JobInfoVO;
import com.ahcloud.edas.powerjob.biz.domain.job.vo.JobVO;
import com.ahcloud.edas.powerjob.biz.infrastructure.constant.enums.PowerJobRetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tech.powerjob.client.PowerJobClient;
import tech.powerjob.common.PageResult;
import tech.powerjob.common.request.http.SaveJobInfoRequest;
import tech.powerjob.common.request.query.JobInfoQuery;
import tech.powerjob.common.response.JobInfoDTO;
import tech.powerjob.common.response.ResultDTO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:52
 **/
@Slf4j
@Component
public class PowerJobManager {
    @Resource
    private PowerJobClient powerJobClient;
    @Resource
    private PowerJobAppInfoManager powerJobAppInfoManager;

    /**
     * 新增job
     *
     * @param form
     */
    public void addJob(JobAddForm form) {
        Long powerJobAppId = powerJobAppInfoManager.getPowerJobAppId(form.getAppId());
        SaveJobInfoRequest request = PowerJobHelper.convert(powerJobAppId, form);
        ResultDTO<Long> resultDTO = powerJobClient.saveJob(request);
        if (!resultDTO.isSuccess() || resultDTO.getData() == null) {
            log.error("PowerJobManager[addJob] saveJob add failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_ADD_FAILED);
        }
    }

    /**
     * 更新job
     *
     * @param form
     */
    public void updateJob(JobUpdateForm form) {
        SaveJobInfoRequest request = PowerJobHelper.convert(form);
        ResultDTO<Long> resultDTO = powerJobClient.saveJob(request);
        if (!resultDTO.isSuccess() || resultDTO.getData() == null) {
            log.error("PowerJobManager[updateJob] saveJob update failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_UPDATE_FAILED);
        }
    }

    /**
     * 根据appId和任务id查询任务详情
     *
     * @param powerJobAppId
     * @param id
     * @return
     */
    public JobInfoVO findJobInfo(Long powerJobAppId, Long id) {
        ResultDTO<JobInfoDTO> resultDTO = powerJobClient.fetchJob(powerJobAppId, id);
        if (!resultDTO.isSuccess() || Objects.isNull(resultDTO.getData())) {
            log.error("PowerJobManager[findJobById] fetchJob failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_INFO_QUERY_FAILED);
        }
        return PowerJobHelper.convertToInfoVO(resultDTO.getData());
    }

    /**
     * 分页查询任务列表
     *
     * @param jobQuery
     * @return
     */
    public PageResult<JobVO> pageJobList(JobQuery jobQuery) {
        JobInfoQuery powerQuery = PowerJobHelper.convertToQuery(jobQuery);
        ResultDTO<PageResult<JobInfoDTO>> resultDTO = powerJobClient.pageJob(powerQuery);
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[pageJobList] pageJob failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_PAGE_QUERY_FAILED);
        }
        PageResult<JobInfoDTO> pageResult = resultDTO.getData();
        List<JobVO> jobVOList = PowerJobHelper.convertToVOList(pageResult.getRows());
        PageResult<JobVO> result = new PageResult<>();
        result.setPages(pageResult.getPages());
        result.setPageNum(jobQuery.getPageNo());
        result.setPageSize(jobQuery.getPageSize());
        result.setTotal(pageResult.getTotal());
        result.setRows(jobVOList);
        return result;
    }

    /**
     * 执行任务
     *
     * @param form
     */
    public void runJob(OperateJobForm form) {
        ResultDTO<Long> resultDTO;
        if (StringUtils.isNotBlank(form.getInstanceParams()) && form.getDelayMS() != null) {
            resultDTO = powerJobClient.runJob(form.getPowerJobAppId(), form.getJobId(), form.getInstanceParams(), form.getDelayMS());
        } else {
            resultDTO = powerJobClient.runJob(form.getPowerJobAppId(), form.getJobId());
        }
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobManager[runJob] runJob failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.JOB_RUN_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[runJob] runJob failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_RUN_FAILED);
        }
    }

    /**
     * 禁止任务
     *
     * @param form
     */
    public void disableJob(OperateJobForm form) {
        ResultDTO<Void> resultDTO = powerJobClient.disableJob(form.getPowerJobAppId(), form.getJobId());
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobManager[disableJob] disableJob failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.JOB_DISABLE_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[disableJob] disableJob failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_DISABLE_FAILED);
        }
    }

    /**
     * 启动任务
     *
     * @param form
     */
    public void enableJob(OperateJobForm form) {
        ResultDTO<Void> resultDTO = powerJobClient.enableJob(form.getPowerJobAppId(), form.getJobId());
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobManager[enableJob] enableJob failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.JOB_ENABLE_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[enableJob] enableJob failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_ENABLE_FAILED);
        }
    }

    /**
     * 删除任务
     *
     * @param form
     */
    public void deleteJob(OperateJobForm form) {
        ResultDTO<Void> resultDTO = powerJobClient.deleteJob(form.getPowerJobAppId(), form.getJobId());
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobManager[deleteJob] deleteJob failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.JOB_DELETE_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[deleteJob] deleteJob failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.JOB_DELETE_FAILED);
        }
    }

}
