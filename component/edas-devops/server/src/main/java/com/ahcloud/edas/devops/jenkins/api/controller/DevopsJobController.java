package com.ahcloud.edas.devops.jenkins.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.devops.jenkins.biz.application.manager.DevopsJobManager;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.form.DevopsAddJobForm;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.query.JobHistoryQuery;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.vo.JobHistoryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 16:53
 **/
@RestController
@RequestMapping("/devops/job")
public class DevopsJobController {
    @Resource
    private DevopsJobManager devopsJobManager;

    /**
     * 新增任务
     * @param form
     * @return jobHistoryId
     */
    @PostMapping("/add")
    public ResponseResult<Long> add(@RequestBody @Valid DevopsAddJobForm form) throws URISyntaxException {
        return ResponseResult.ofSuccess(devopsJobManager.addJob(form));
    }

    /**
     * 终止任务
     * @param id
     * @return
     */
    @PostMapping("/aborted/{id}")
    public ResponseResult<Void> abortedJob(@PathVariable("id") Long id) throws Exception {
        devopsJobManager.abortedJob(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询任务状态
     * @param appId
     * @return
     */
    @GetMapping("/queryJobStatus/{appId}")
    public ResponseResult<Integer> queryJobStatus(@PathVariable("appId") Long appId) {
        return ResponseResult.ofSuccess(devopsJobManager.queryJobStatus(appId));
    }

    /**
     * 分页查询历史任务列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<JobHistoryVO>> page(JobHistoryQuery query) {
        return ResponseResult.ofSuccess(devopsJobManager.pageJobList(query));
    }

    /**
     * 根据id查询历史任务
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<JobHistoryVO> findById(@PathVariable("id") Long id) {
        return ResponseResult.ofSuccess(devopsJobManager.findJobById(id));
    }

    /**
     * 获取控制台输出
     * @param id
     * @return
     */
    @GetMapping("/getConsoleOutput/{id}")
    public ResponseResult<String> getConsoleOutput(@PathVariable("id") Long id) throws Exception {
        return ResponseResult.ofSuccess(devopsJobManager.getConsoleOutput(id));
    }

    /**
     * 流式查询控制台输出
     * @param response
     * @param id
     */
    @GetMapping("/streamConsoleOutput/{id}")
    public void streamConsoleOutput(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        devopsJobManager.streamConsoleOutput(response, id);
    }
}
