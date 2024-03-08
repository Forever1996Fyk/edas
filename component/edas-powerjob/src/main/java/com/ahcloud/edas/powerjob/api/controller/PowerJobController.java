package com.ahcloud.edas.powerjob.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.powerjob.biz.application.manager.PowerJobManager;
import com.ahcloud.edas.powerjob.biz.domain.job.form.JobAddForm;
import com.ahcloud.edas.powerjob.biz.domain.job.form.JobUpdateForm;
import com.ahcloud.edas.powerjob.biz.domain.job.form.OperateJobForm;
import com.ahcloud.edas.powerjob.biz.domain.job.query.JobQuery;
import com.ahcloud.edas.powerjob.biz.domain.job.vo.JobInfoVO;
import com.ahcloud.edas.powerjob.biz.domain.job.vo.JobVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.powerjob.common.PageResult;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/21 18:35
 **/
@RestController
@RequestMapping("/powerjob")
public class PowerJobController {
    @Resource
    private PowerJobManager powerJobManager;

    /**
     * 新增任务
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> addJob(@RequestBody @Valid JobAddForm form) {
        powerJobManager.addJob(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新任务
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> updateJob(@RequestBody @Valid JobUpdateForm form) {
        powerJobManager.updateJob(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询任务详情
     * @param powerJobAppId
     * @param jobId
     * @return
     */
    @GetMapping("/findJobInfo")
    public ResponseResult<JobInfoVO> findJobInfo(@RequestParam("powerJobAppId") Long powerJobAppId, @RequestParam("jobId") Long jobId) {
        return ResponseResult.ofSuccess(powerJobManager.findJobInfo(powerJobAppId, jobId));
    }

    /**
     * 分页查询任务列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<JobVO>> pageJobList(JobQuery query) {
        return ResponseResult.ofSuccess(powerJobManager.pageJobList(query));
    }

    /**
     * 执行任务
     * @param form
     * @return
     */
    @PostMapping("/runJob")
    public ResponseResult<Void> runJob(@RequestBody @Valid OperateJobForm form) {
        powerJobManager.runJob(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 禁止任务
     * @param form
     * @return
     */
    @PostMapping("/disableJob")
    public ResponseResult<Void> disableJob(@RequestBody @Valid OperateJobForm form) {
        powerJobManager.disableJob(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 启动任务
     * @param form
     * @return
     */
    @PostMapping("/enableJob")
    public ResponseResult<Void> enableJob(@RequestBody @Valid OperateJobForm form) {
        powerJobManager.enableJob(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除任务
     * @param form
     * @return
     */
    @PostMapping("/deleteJob")
    public ResponseResult<Void> deleteJob(@RequestBody @Valid OperateJobForm form) {
        powerJobManager.deleteJob(form);
        return ResponseResult.ofSuccess();
    }


}
