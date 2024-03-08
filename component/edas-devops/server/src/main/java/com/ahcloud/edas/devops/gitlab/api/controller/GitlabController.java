package com.ahcloud.edas.devops.gitlab.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.devops.gitlab.biz.application.manager.GitlabManager;
import com.ahcloud.edas.devops.gitlab.domain.query.ProjectQuery;
import com.ahcloud.edas.devops.gitlab.domain.vo.BranchSelectVO;
import com.ahcloud.edas.devops.gitlab.domain.vo.ProjectSelectVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 10:25
 **/
@RestController
@RequestMapping("/devops/gitlab")
public class GitlabController {
    @Resource
    private GitlabManager gitlabManager;

    /**
     * 查询gitlab项目选择列表
     * @param query
     * @return
     */
    @GetMapping("/getProjectSelectList")
    public ResponseResult<List<ProjectSelectVO>> getProjectSelectList(ProjectQuery query) {
        return ResponseResult.ofSuccess(gitlabManager.getProjectSelectList(query));
    }

    /**
     * 查询分支选择列表
     * @param appId
     * @param name
     * @return
     */
    @GetMapping("/getBranchSelectList")
    public ResponseResult<List<BranchSelectVO>> getBranchSelectList(
            @RequestParam("appId") Long appId,
            @RequestParam(value = "name", required = false) String name) {
        return ResponseResult.ofSuccess(gitlabManager.getBranchSelectList(appId, name));
    }
}
