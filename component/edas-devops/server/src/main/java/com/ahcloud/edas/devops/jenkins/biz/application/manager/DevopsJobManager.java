package com.ahcloud.edas.devops.jenkins.biz.application.manager;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.AppTypeEnum;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.DateUtils;
import com.ahcloud.edas.common.util.JsonUtils;
import com.ahcloud.edas.devops.config.biz.application.service.DevopsConfigService;
import com.ahcloud.edas.devops.config.biz.domain.config.dto.DevelopmentConfig;
import com.ahcloud.edas.devops.jenkins.biz.application.service.DevopsJobHistoryService;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.dto.JenkinsConfig;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.dto.JenkinsJobParams;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.form.DevopsAddJobForm;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.query.JobHistoryQuery;
import com.ahcloud.edas.devops.jenkins.biz.domain.job.vo.JobHistoryVO;
import com.ahcloud.edas.devops.jenkins.core.JenkinsConnector;
import com.ahcloud.edas.devops.jenkins.core.job.JobExecutor;
import com.ahcloud.edas.devops.config.biz.infrastructure.repository.bean.DevopsConfig;
import com.ahcloud.edas.devops.jenkins.biz.infrastructure.repository.bean.DevopsJobHistory;
import com.ahcloud.edas.devops.jenkins.biz.infrastructure.util.PageUtils;
import com.ahcloud.edas.devops.jenkins.core.job.enums.JenkinsRetCodeEnum;
import com.ahcloud.edas.devops.jenkins.core.job.enums.JobStatusEnum;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 23:12
 **/
@Slf4j
@Component
public class DevopsJobManager {
    @Resource
    private DevopsConfigService devopsConfigService;
    @Resource
    private DevopsJobHistoryService devopsJobHistoryService;

    private final static Lock ADD_LOCK = new ReentrantLock();
    private final static Lock ABORTED_LOCK = new ReentrantLock();

    private final static int DEFAULT_QUEUE_SIZE = Integer.MAX_VALUE;

    private final static long DEFAULT_TIMEOUT_SECONDS = 10 * 60;
    /**
     * job任务线程池
     * updateUserThreadPool
     */
    public static ThreadPoolExecutor JOB_POOL = new ThreadPoolExecutor(
            20,
            20,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "JOB_POOL-" + r.hashCode()));

    /**
     * job任务状态同步线程池
     * updateUserThreadPool
     */
    public static ThreadPoolExecutor JOB_STATUS_SYNC_POOL = new ThreadPoolExecutor(
            50,
            50,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(DEFAULT_QUEUE_SIZE),
            r -> new Thread(r, "JOB_STATUS_SYNC_POOL-" + r.hashCode()));

    /**
     * 新增任务
     */
    @Transactional(rollbackFor = Exception.class)
    public Long addJob(DevopsAddJobForm form) {
        boolean b = ADD_LOCK.tryLock();
        try {
            if (b) {
                DevopsConfig devopsConfig = devopsConfigService.getOne(
                        new QueryWrapper<DevopsConfig>().lambda()
                                .eq(DevopsConfig::getAppId, form.getAppId())
                                .eq(DevopsConfig::getDeleted, DeletedEnum.NO.value)
                );
                if (Objects.isNull(devopsConfig)) {
                    throw new BizException(JenkinsRetCodeEnum.DATA_NOT_EXISTED);
                }
                List<DevopsJobHistory> exitedNotBuildJobList = devopsJobHistoryService.list(
                        new QueryWrapper<DevopsJobHistory>().lambda()
                                .eq(DevopsJobHistory::getAppId, form.getAppId())
                                .in(DevopsJobHistory::getStatus, JobStatusEnum.getProcessNodes())
                );
                if (CollectionUtils.isNotEmpty(exitedNotBuildJobList)) {
                    throw new BizException(JenkinsRetCodeEnum.JOB_BUILDING_REPEATED);
                }
                String config = devopsConfig.getJenkinsConfig();
                JenkinsConfig jenkinsConfig = JsonUtils.stringToBean(config, JenkinsConfig.class);
                if (Objects.isNull(jenkinsConfig)) {
                    throw new BizException(JenkinsRetCodeEnum.JOB_CONFIG_NOT_EXISTED);
                }
                Map<String, String> params = buildParams(form.getParams(), devopsConfig);
                DevopsJobHistory devopsJobHistory = new DevopsJobHistory();
                devopsJobHistory.setAppId(form.getAppId());
                devopsJobHistory.setJobName(jenkinsConfig.getJobName());
                devopsJobHistory.setConfig(config);
                devopsJobHistory.setParams(JsonUtils.toJsonString(params));
                devopsJobHistory.setDescription(form.getDescription());
                devopsJobHistory.setStatus(JobStatusEnum.NOT_BUILT.getStatus());
                devopsJobHistory.setModifier(UserUtils.getUserNameBySession());
                devopsJobHistory.setCreator(UserUtils.getUserNameBySession());
                devopsJobHistoryService.save(devopsJobHistory);
                // 事务提交后执行
                TransactionSynchronizationManager.registerSynchronization(new AfterSaveDevopsJobTransactionSynchronization(params, jenkinsConfig, devopsJobHistory));
                return devopsJobHistory.getId();
            } else {
                log.warn("当前应用[{}]正在构建。。。", form.getAppId());
                throw new BizException(JenkinsRetCodeEnum.JOB_BUILDING_REPEATED);
            }
        } finally {
            ADD_LOCK.unlock();
        }
    }

    /**
     * 终止任务
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void abortedJob(Long id) throws Exception {
        boolean b = ABORTED_LOCK.tryLock();
        try {
            if (b) {
                DevopsJobHistory devopsJobHistory = devopsJobHistoryService.getOne(
                        new QueryWrapper<DevopsJobHistory>().lambda()
                                .eq(DevopsJobHistory::getId, id)
                                .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
                );
                Integer status = devopsJobHistory.getStatus();
                JobStatusEnum jobStatusEnum = JobStatusEnum.getByStatus(status);
                if (jobStatusEnum.isEndNodes()) {
                    throw new BizException(JenkinsRetCodeEnum.JOB_ENDED);
                }
                String config = devopsJobHistory.getConfig();
                JenkinsConfig jenkinsConfig = JsonUtils.stringToBean(config, JenkinsConfig.class);
                if (Objects.isNull(jenkinsConfig)) {
                    throw new BizException(JenkinsRetCodeEnum.JOB_CONFIG_NOT_EXISTED);
                }
                JenkinsConnector connector = new JenkinsConnector(jenkinsConfig.getJenkinsUrl(), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
                JobExecutor executor = JobExecutor.build(connector.connector());
                try {
                    executor.abortedJob(devopsJobHistory.getJobName(), devopsJobHistory.getVersion());
                } catch (IOException e) {
                    log.error("任务[{}]终止失败，原因为:{}", devopsJobHistory.getJobName(), Throwables.getStackTraceAsString(e));
                    throw new BizException(JenkinsRetCodeEnum.JOB_ABORTED_FAILED);
                }
                DevopsJobHistory update = new DevopsJobHistory();
                update.setId(devopsJobHistory.getId());
                update.setStatus(JobStatusEnum.ABORTED.getStatus());
                update.setAbortTime(new Date());
                update.setModifier(UserUtils.getUserNameBySession());
                devopsJobHistoryService.updateById(update);
            } else {
                throw new BizException(JenkinsRetCodeEnum.JOB_ABORTED_REPEATED);
            }
        } finally {
            ABORTED_LOCK.unlock();
        }
    }

    /**
     * 查询任务状态
     *
     * @param appId
     * @return
     */
    public int queryJobStatus(Long appId) {
        DevopsJobHistory devopsJobHistory = devopsJobHistoryService.getOne(
                new QueryWrapper<DevopsJobHistory>().lambda()
                        .eq(DevopsJobHistory::getAppId, appId)
                        .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
        );
        JobStatusEnum jobStatusEnum = JobStatusEnum.getByStatus(devopsJobHistory.getStatus());
        return jobStatusEnum.getStatus();
    }

    /**
     * 同步任务状态
     *
     * @param id
     */
    public void syncJobStatus(Long id) {
        JOB_STATUS_SYNC_POOL.execute(() -> {
            long countSec = 0;
            Instant now = Instant.now();
            while (countSec <= DEFAULT_TIMEOUT_SECONDS) {
                try {
                    // 等待5秒，保证构建号保存成功
                    TimeUnit.SECONDS.sleep(5);
                    DevopsJobHistory devopsJobHistory = devopsJobHistoryService.getOne(
                            new QueryWrapper<DevopsJobHistory>().lambda()
                                    .eq(DevopsJobHistory::getId, id)
                                    .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
                    );
                    if (Objects.isNull(devopsJobHistory)) {
                        log.error("SyncJobStatusTask run exception, cause is devopsJob not existed");
                        break;
                    }
                    if (Objects.isNull(devopsJobHistory.getVersion())) {
                        log.error("SyncJobStatusTask run exception, cause is job buildNumber not updated");
                        continue;
                    }
                    JobStatusEnum oldJobStatus = JobStatusEnum.getByStatus(devopsJobHistory.getStatus());
                    if (oldJobStatus.isEndNodes()) {
                        log.info("SyncJobStatusTask run finish, jobStatus is {}", oldJobStatus);
                        break;
                    }
                    String config = devopsJobHistory.getConfig();
                    JenkinsConfig jenkinsConfig = JsonUtils.stringToBean(config, JenkinsConfig.class);
                    if (Objects.isNull(jenkinsConfig)) {
                        log.error("SyncJobStatusTask run exception, cause is jenkinsConfig not existed");
                        break;
                    }
                    JenkinsConnector connector = new JenkinsConnector(jenkinsConfig.getJenkinsUrl(), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
                    JobExecutor executor = JobExecutor.build(connector.connector());
                    JobWithDetails job = executor.queryJob(devopsJobHistory.getJobName());
                    Build build = job.getBuildByNumber(devopsJobHistory.getVersion().intValue());
                    if (Objects.isNull(build)) {
                        continue;
                    }
                    BuildWithDetails details = build.details();
                    BuildResult result = details.getResult();
                    if (Objects.isNull(result)) {
                        continue;
                    }
                    JobStatusEnum newJobStatus = convertJobStatus(result);
                    if (oldJobStatus != newJobStatus) {
                        DevopsJobHistory update = new DevopsJobHistory();
                        update.setId(devopsJobHistory.getId());
                        update.setStatus(newJobStatus.getStatus());
                        update.setDuration(details.getDuration());
                        update.setEstimatedDuration(details.getEstimatedDuration());
                        if (newJobStatus.isEndNodes()) {
                            update.setEndTime(new Date());
                        }
                        devopsJobHistoryService.updateById(update);
                    }
                    if (newJobStatus.isEndNodes()) {
                        log.info("SyncJobStatusTask run finish, jobStatus is {}", newJobStatus);
                        break;
                    }
                    countSec = ChronoUnit.SECONDS.between(now, Instant.now());
                    log.info("SyncJobStatusTask running, execution time is {}", countSec);
                } catch (Exception e) {
                    log.error("SyncJobStatusTask run exception, cause is {}", Throwables.getStackTraceAsString(e));
                    break;
                }
            }
        });
    }

    /**
     * 分页查询历史任务列表
     *
     * @param query
     * @return
     */
    public PageResult<JobHistoryVO> pageJobList(JobHistoryQuery query) {
        PageInfo<DevopsJobHistory> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> devopsJobHistoryService.list(
                                new QueryWrapper<DevopsJobHistory>().lambda()
                                        .orderByDesc(DevopsJobHistory::getId)
                                        .eq(DevopsJobHistory::getAppId, query.getAppId())
                                        .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
                        )
                );
        List<DevopsJobHistory> pageInfoList = pageInfo.getList();
        List<JobHistoryVO> jobHistoryList = Lists.newArrayList();
        for (DevopsJobHistory devopsJobHistory : pageInfoList) {
            JobHistoryVO jobHistoryVO = Convert.convert(JobHistoryVO.class, devopsJobHistory);
            jobHistoryVO.setDuration(DateUtils.convertToMinAndSec(devopsJobHistory.getDuration()));
            jobHistoryVO.setEstimatedDuration(DateUtils.convertToMinAndSec(devopsJobHistory.getEstimatedDuration()));
            JobStatusEnum jobStatusEnum = JobStatusEnum.getByStatus(jobHistoryVO.getStatus());
            jobHistoryVO.setStatusName(jobStatusEnum.getDesc());
            jobHistoryVO.setEnableAbort(jobStatusEnum.enableAbort());
            jobHistoryList.add(jobHistoryVO);
        }
        return PageUtils.pageInfoConvertToPageResult(pageInfo, jobHistoryList);
    }

    /**
     * 根据id查询历史任务
     *
     * @param id
     * @return
     */
    public JobHistoryVO findJobById(Long id) {
        DevopsJobHistory devopsJobHistory = devopsJobHistoryService.getOne(
                new QueryWrapper<DevopsJobHistory>().lambda()
                        .eq(DevopsJobHistory::getId, id)
                        .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
        );
        return Convert.convert(JobHistoryVO.class, devopsJobHistory);
    }

    /**
     * 获取控制台输出
     *
     * @param id
     * @return
     * @throws Exception
     */
    public String getConsoleOutput(Long id) throws Exception {
        DevopsJobHistory devopsJobHistory = devopsJobHistoryService.getOne(
                new QueryWrapper<DevopsJobHistory>().lambda()
                        .eq(DevopsJobHistory::getId, id)
                        .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(devopsJobHistory.getVersion())) {
            return "";
        }
        String config = devopsJobHistory.getConfig();
        JenkinsConfig jenkinsConfig = JsonUtils.stringToBean(config, JenkinsConfig.class);
        if (Objects.isNull(jenkinsConfig)) {
            throw new BizException(JenkinsRetCodeEnum.JOB_CONFIG_NOT_EXISTED);
        }
        JenkinsConnector connector = new JenkinsConnector(jenkinsConfig.getJenkinsUrl(), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
        JobExecutor executor = JobExecutor.build(connector.connector());
        BuildWithDetails details = executor.queryJobBuildDetail(devopsJobHistory.getJobName(), devopsJobHistory.getVersion());
        return details.getConsoleOutputHtml();
    }

    public void streamConsoleOutput(HttpServletResponse response, Long id) throws Exception {
        DevopsJobHistory devopsJobHistory = devopsJobHistoryService.getOne(
                new QueryWrapper<DevopsJobHistory>().lambda()
                        .eq(DevopsJobHistory::getId, id)
                        .eq(DevopsJobHistory::getDeleted, DeletedEnum.NO.value)
        );
        String config = devopsJobHistory.getConfig();
        JenkinsConfig jenkinsConfig = JsonUtils.stringToBean(config, JenkinsConfig.class);
        JenkinsConnector connector = new JenkinsConnector(jenkinsConfig.getJenkinsUrl(), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
        JobExecutor executor = JobExecutor.build(connector.connector());
        executor.streamConsoleOutput(response, devopsJobHistory.getJobName(), devopsJobHistory.getVersion());
    }

    public JobStatusEnum convertJobStatus(BuildResult result) {
        switch (result) {
            case FAILURE:
                return JobStatusEnum.FAILURE;
            case REBUILDING:
                return JobStatusEnum.REBUILD;
            case BUILDING:
                return JobStatusEnum.BUILDING;
            case ABORTED:
                return JobStatusEnum.ABORTED;
            case NOT_BUILT:
                return JobStatusEnum.NOT_BUILT;
            case CANCELLED:
                return JobStatusEnum.CANCELLED;
            default:
                return JobStatusEnum.SUCCESS;
        }
    }

    public void startJobAndUpdateStatus(JenkinsConfig jenkinsConfig, DevopsJobHistory devopsJobHistory, Map<String, String> params) {
        DevopsJobHistory update = new DevopsJobHistory();
        update.setId(devopsJobHistory.getId());
        update.setStartTime(new Date());
        JobStatusEnum jobStatus = JobStatusEnum.BUILDING;
        try {
            JenkinsConnector connector = new JenkinsConnector(jenkinsConfig.getJenkinsUrl(), jenkinsConfig.getUsername(), jenkinsConfig.getPassword());
            JobExecutor executor = JobExecutor.build(connector.connector());
            Long buildNumber = executor.startJob(jenkinsConfig.getJobName(), params);
            update.setVersion(buildNumber);
        } catch (Exception e) {
            String stackTraceAsString = Throwables.getStackTraceAsString(e);
            log.error("构建任务[{}]失败， 原因为:{}", devopsJobHistory.getJobName(), stackTraceAsString);
            update.setReason(stackTraceAsString);
        }
        update.setStatus(jobStatus.getStatus());
        devopsJobHistoryService.updateById(update);
    }


    private Map<String, String> buildParams(JenkinsJobParams params, DevopsConfig devopsConfig) {
        Map<String, String> result = Maps.newHashMap();
        result.put("branch", params.getBranch());
        result.put("env", devopsConfig.getEnv());
        String config = devopsConfig.getDevelopmentConfig();
        if (StringUtils.isNotBlank(config)) {
            Integer type = devopsConfig.getType();
            AppTypeEnum appTypeEnum = AppTypeEnum.getByType(type);
            if (appTypeEnum.isJava()) {
                DevelopmentConfig developmentConfig = JsonUtils.stringToBean(config, DevelopmentConfig.class);
                if (Objects.nonNull(developmentConfig)) {
                    result.put("Xmx", developmentConfig.getJvmConfig().getXmx());
                    result.put("Xms", developmentConfig.getJvmConfig().getXms());
                    result.put("Gc", developmentConfig.getJvmConfig().getGc());
                    result.put("Compressed", developmentConfig.getJvmConfig().getCompressed());

                    result.put("nacosUrl", developmentConfig.getNacosConfig().getNacosUrl());
                    result.put("namespace", developmentConfig.getNacosConfig().getNamespace());
                    result.put("username", developmentConfig.getNacosConfig().getUsername());
                    result.put("password", developmentConfig.getNacosConfig().getPassword());
                }
            }
        }
        return result;
    }

    private class AfterSaveDevopsJobTransactionSynchronization implements TransactionSynchronization {
        private final Map<String, String> params;
        private final JenkinsConfig jenkinsConfig;
        private final DevopsJobHistory devopsJobHistory;

        public AfterSaveDevopsJobTransactionSynchronization(Map<String, String> params, JenkinsConfig jenkinsConfig, DevopsJobHistory devopsJobHistory) {
            this.params = params;
            this.jenkinsConfig = jenkinsConfig;
            this.devopsJobHistory = devopsJobHistory;
        }

        @Override
        public void afterCommit() {
            // 启动并更新状态
            JOB_POOL.execute(() -> startJobAndUpdateStatus(jenkinsConfig, devopsJobHistory, params));
            // 同步状态
            JOB_STATUS_SYNC_POOL.execute(() -> syncJobStatus(devopsJobHistory.getId()));
        }
    }
}
