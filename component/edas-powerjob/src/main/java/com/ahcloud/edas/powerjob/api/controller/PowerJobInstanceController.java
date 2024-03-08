package com.ahcloud.edas.powerjob.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.powerjob.biz.application.manager.PowerJobInstanceManager;
import com.ahcloud.edas.powerjob.biz.domain.instance.form.OperateInstanceForm;
import com.ahcloud.edas.powerjob.biz.domain.instance.query.JobInstanceQuery;
import com.ahcloud.edas.powerjob.biz.domain.instance.vo.JobInstanceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.powerjob.common.PageResult;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/22 11:06
 **/
@RestController
@RequestMapping("/powerjob/instance")
public class PowerJobInstanceController {
    @Resource
    private PowerJobInstanceManager powerJobInstanceManager;

    /**
     * 分页查询实例列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<JobInstanceVO>> pageInstanceList(JobInstanceQuery query) {
        return ResponseResult.ofSuccess(powerJobInstanceManager.pageJobInstanceList(query));
    }

    /**
     * 停止实例
     * @param form
     * @return
     */
    @PostMapping("/stop")
    public ResponseResult<Void> stopInstance(@RequestBody @Valid OperateInstanceForm form) {
        powerJobInstanceManager.stopInstance(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 取消实例
     * @param form
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult<Void> cancelInstance(@RequestBody @Valid OperateInstanceForm form) {
        powerJobInstanceManager.cancelInstance(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 重试实例
     * @param form
     * @return
     */
    @PostMapping("/retry")
    public ResponseResult<Void> retryInstance(@RequestBody @Valid OperateInstanceForm form) {
        powerJobInstanceManager.retryInstance(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询实例状态
     * @param instanceId
     * @return
     */
    @GetMapping("/queryStatus/{instanceId}")
    public ResponseResult<Integer> queryInstanceStatus(@PathVariable("instanceId") Long instanceId) {
        return ResponseResult.ofSuccess(powerJobInstanceManager.queryInstanceStatus(instanceId));
    }

    /**
     * 查询实例详情
     * @param instanceId
     * @return
     */
    @GetMapping("/queryInfo/{instanceId}")
    public ResponseResult<JobInstanceVO> queryInstanceInfo(@PathVariable("instanceId") Long instanceId) {
        return ResponseResult.ofSuccess(powerJobInstanceManager.queryInstanceInfo(instanceId));
    }
}
