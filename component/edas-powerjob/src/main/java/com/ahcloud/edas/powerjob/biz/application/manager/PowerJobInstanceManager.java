package com.ahcloud.edas.powerjob.biz.application.manager;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.powerjob.biz.application.helper.PowerJobInstanceHelper;
import com.ahcloud.edas.powerjob.biz.domain.instance.form.OperateInstanceForm;
import com.ahcloud.edas.powerjob.biz.domain.instance.query.JobInstanceQuery;
import com.ahcloud.edas.powerjob.biz.domain.instance.vo.JobInstanceVO;
import com.ahcloud.edas.powerjob.biz.infrastructure.constant.enums.PowerJobRetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.powerjob.client.PowerJobClient;
import tech.powerjob.common.PageResult;
import tech.powerjob.common.request.query.InstanceQuery;
import tech.powerjob.common.response.InstanceInfoDTO;
import tech.powerjob.common.response.ResultDTO;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 09:39
 **/
@Slf4j
@Component
public class PowerJobInstanceManager {
    @Resource
    private PowerJobClient powerJobClient;

    /**
     * 分页查询任务实例
     * @param query
     * @return
     */
    public PageResult<JobInstanceVO> pageJobInstanceList(JobInstanceQuery query) {
        InstanceQuery powerQuery = PowerJobInstanceHelper.convert(query);
        ResultDTO<PageResult<InstanceInfoDTO>> resultDTO = powerJobClient.pageInstance(powerQuery);
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobInstanceManager[pageInstance] pageInstance failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_PAGE_QUERY_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[pageInstance] pageInstance failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_PAGE_QUERY_FAILED);
        }
        PageResult<InstanceInfoDTO> pageResult = resultDTO.getData();
        PageResult<JobInstanceVO> result = new PageResult<>();
        result.setPages(pageResult.getPages());
        result.setPageNum(query.getPageNo());
        result.setPageSize(query.getPageSize());
        result.setTotal(pageResult.getTotal());
        result.setRows(PowerJobInstanceHelper.convertToVOList(pageResult.getRows()));
        return result;
    }

    /**
     * 停止实例
     * @param form
     */
    public void stopInstance(OperateInstanceForm form) {
        ResultDTO<Void> resultDTO = powerJobClient.stopInstance(form.getPowerJobAppId(), form.getInstanceId());
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobInstanceManager[stopInstance] stopInstance failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_STOP_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[stopInstance] stopInstance failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_STOP_FAILED);
        }
    }

    /**
     * 取消实例
     * @param form
     */
    public void cancelInstance(OperateInstanceForm form) {
        ResultDTO<Void> resultDTO = powerJobClient.cancelInstance(form.getPowerJobAppId(), form.getInstanceId());
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobInstanceManager[cancelInstance] cancelInstance failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_CANCEL_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[cancelInstance] cancelInstance failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_CANCEL_FAILED);
        }
    }

    /**
     * 重试实例
     * @param form
     */
    public void retryInstance(OperateInstanceForm form) {
        ResultDTO<Void> resultDTO = powerJobClient.retryInstance(form.getPowerJobAppId(), form.getInstanceId());
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobInstanceManager[retryInstance] retryInstance failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_RETRY_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[cancelInstance] retryInstance failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_RETRY_FAILED);
        }
    }

    /**
     * 查询实例状态
     * @param instanceId
     */
    public Integer queryInstanceStatus(Long instanceId) {
        ResultDTO<Integer> resultDTO = powerJobClient.fetchInstanceStatus(instanceId);
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobInstanceManager[queryInstanceStatus] fetchInstanceStatus failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_QUERY_STATUS_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[queryInstanceStatus] fetchInstanceStatus failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_QUERY_STATUS_FAILED);
        }
        return resultDTO.getData();
    }

    /**
     * 查询实例信息
     * @param instanceId
     */
    public JobInstanceVO queryInstanceInfo(Long instanceId) {
        ResultDTO<InstanceInfoDTO> resultDTO = powerJobClient.fetchInstanceInfo(instanceId);
        if (Objects.isNull(resultDTO)) {
            log.error("PowerJobInstanceManager[queryInstanceInfo] fetchInstanceInfo failed. cause is result null");
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_QUERY_STATUS_FAILED);
        }
        if (!resultDTO.isSuccess()) {
            log.error("PowerJobManager[queryInstanceInfo] fetchInstanceInfo failed. cause is {}", resultDTO.getMessage());
            throw new BizException(PowerJobRetCodeEnum.INSTANCE_QUERY_STATUS_FAILED);
        }
        return PowerJobInstanceHelper.convertToVO(resultDTO.getData());
    }

}
