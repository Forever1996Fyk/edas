package com.ahcloud.edas.devops.gitlab.biz.application.manager;

import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.devops.config.biz.application.service.DevopsConfigService;
import com.ahcloud.edas.devops.config.biz.infrastructure.constant.enums.DevopsRetCodeEnum;
import com.ahcloud.edas.devops.config.biz.infrastructure.repository.bean.DevopsConfig;
import com.ahcloud.edas.devops.gitlab.biz.domain.dto.GitlabConfig;
import com.ahcloud.edas.devops.gitlab.core.GitlabApiService;
import com.ahcloud.edas.devops.gitlab.core.enums.GitlabRetCodeEnum;
import com.ahcloud.edas.devops.gitlab.domain.query.BranchQuery;
import com.ahcloud.edas.devops.gitlab.domain.query.ProjectQuery;
import com.ahcloud.edas.devops.gitlab.domain.vo.BranchSelectVO;
import com.ahcloud.edas.devops.gitlab.domain.vo.ProjectSelectVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 10:25
 **/
@Slf4j
@Component
public class GitlabManager {
    @Resource
    private GitlabApiService gitlabApiService;
    @Resource
    private DevopsConfigService devopsConfigService;

    /**
     * 查询gitlab项目选择列表
     * @param query
     * @return
     */
    public List<ProjectSelectVO> getProjectSelectList(ProjectQuery query) {
        try {
            return gitlabApiService.queryProjectSelectList(query);
        } catch (GitLabApiException e) {
            log.error("GitlabManager[getProjectSelectList] 查询gitlab项目列表异常[{}]，原因为: {}", query, Throwables.getStackTraceAsString(e));
            throw new BizException(GitlabRetCodeEnum.GITLAB_PROJECT_QUERY_FAILED);
        }
    }

    /**
     * 查询分支选择列表
     * @param appId
     * @param name
     * @return
     */
    public List<BranchSelectVO> getBranchSelectList(Long appId, String name) {
        DevopsConfig devopsConfig = devopsConfigService.getOne(
                new QueryWrapper<DevopsConfig>().lambda()
                        .eq(DevopsConfig::getAppId, appId)
                        .eq(DevopsConfig::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(devopsConfig)) {
            throw new BizException(DevopsRetCodeEnum.DATA_NOT_EXISTED);
        }
        String config = devopsConfig.getGitlabConfig();
        if (StringUtils.isBlank(config)) {
            throw new BizException(DevopsRetCodeEnum.CONFIG_NOT_EXISTED);
        }
        GitlabConfig gitlabConfig = JsonUtils.stringToBean(config, GitlabConfig.class);
        if (Objects.isNull(gitlabConfig)) {
            throw new BizException(DevopsRetCodeEnum.CONFIG_NOT_EXISTED);
        }
        BranchQuery branchQuery = new BranchQuery();
        branchQuery.setProjectId(gitlabConfig.getProjectId());
        branchQuery.setName(name);
        try {
            return gitlabApiService.queryBranchSelectList(branchQuery);
        } catch (GitLabApiException e) {
            log.error("GitlabManager[getBranchSelectList] 查询gitlab分支列表异常[appId:{}, projectName:{}]，原因为: {}", appId, gitlabConfig.getName(), Throwables.getStackTraceAsString(e));
            throw new BizException(GitlabRetCodeEnum.GITLAB_BRANCH_QUERY_FAILED);
        }
    }

    /**
     * 获取保护分支
     * @param appId
     * @return
     */
    public List<BranchSelectVO> getProtectedBranchList(Long appId) {
        List<BranchSelectVO> branchSelectList = this.getBranchSelectList(appId, null);
        return branchSelectList.stream().filter(BranchSelectVO::getProtectedBranch).collect(Collectors.toList());
    }
}
