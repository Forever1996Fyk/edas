package com.ahcloud.edas.devops.gitlab.core;

import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.devops.gitlab.domain.query.BranchQuery;
import com.ahcloud.edas.devops.gitlab.domain.query.ProjectQuery;
import com.ahcloud.edas.devops.gitlab.domain.vo.BranchSelectVO;
import com.ahcloud.edas.devops.gitlab.domain.vo.ProjectSelectVO;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.RepositoryApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 10:09
 **/
public class GitlabApiService {

    private final GitLabApi gitLabApi;

    public GitlabApiService(GitlabConnector connector) {
        this.gitLabApi = connector.build();
    }

    /**
     * 查询gitlab 项目选择列表
     * @param query
     * @return
     * @throws GitLabApiException
     */
    public List<ProjectSelectVO> queryProjectSelectList(ProjectQuery query) throws GitLabApiException {
        List<Project> projects = gitLabApi.getProjectApi().getProjects(query.getName());
        if (CollectionUtils.isEmpty(projects)) {
            return Collections.emptyList();
        }
        return  projects.stream()
                .map(project ->
                        ProjectSelectVO.builder()
                                .projectId(project.getId())
                                .name(project.getName())
                                .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * 获取分支选择列表
     * @param query
     * @return
     * @throws GitLabApiException
     */
    public List<BranchSelectVO> queryBranchSelectList(BranchQuery query) throws GitLabApiException {
        RepositoryApi repositoryApi = gitLabApi.getRepositoryApi();
        List<Branch> branchList = repositoryApi.getBranches(query.getProjectId(), query.getName());
        if (CollectionUtils.isEmpty(branchList)) {
            return Collections.emptyList();
        }
        return branchList.stream()
                .map(branch -> BranchSelectVO.builder().name(branch.getName()).protectedBranch(branch.getProtected()).build())
                .collect(Collectors.toList());
    }
}
