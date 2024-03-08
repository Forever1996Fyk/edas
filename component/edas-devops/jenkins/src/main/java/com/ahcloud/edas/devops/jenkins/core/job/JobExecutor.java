package com.ahcloud.edas.devops.jenkins.core.job;

import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.devops.jenkins.core.JenkinsConnector;
import com.ahcloud.edas.devops.jenkins.core.job.enums.JenkinsRetCodeEnum;
import com.ahcloud.edas.devops.jenkins.core.job.extend.CustomBuildWithDetails;
import com.ahcloud.edas.devops.jenkins.core.job.listener.ConsoleStreamListener;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Artifact;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildChangeSet;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Executable;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.MavenBuild;
import com.offbytwo.jenkins.model.MavenJobWithDetails;
import com.offbytwo.jenkins.model.MavenModule;
import com.offbytwo.jenkins.model.MavenModuleRecord;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 20:10
 **/
@Slf4j
public class JobExecutor {

    private final JenkinsServer jenkinsServer;
    private final static int DEFAULT_LOOP_COUNT = 10;

    public JobExecutor(JenkinsConnector jenkinsConnector) {
        this.jenkinsServer = jenkinsConnector.getJenkinsServer();
    }

    public static JobExecutor build(JenkinsConnector connector) {
        return new JobExecutor(connector);
    }

    /**
     * 构建jenkins job
     * @param jobName
     * @param params
     * @return jobId
     * @throws IOException
     */
    public Long startJob(String jobName, Map<String, String> params) throws IOException, InterruptedException {
        JenkinsServer jenkinsServer = this.jenkinsServer;
        JobWithDetails job = jenkinsServer.getJob(jobName);
        QueueReference reference = job.build(params, true);
        QueueItem queueItem = jenkinsServer.getQueueItem(reference);
        Executable executable = queueItem.getExecutable();
        int count = 0;
        while (Objects.isNull(executable) && count <= DEFAULT_LOOP_COUNT) {
            TimeUnit.SECONDS.sleep(5L);
            log.info("time wait query job building");
            executable = jenkinsServer.getQueueItem(reference).getExecutable();
            count++;
        }
        Long number = executable.getNumber();
        log.info("this jobId is {}", number);
        return number;
    }

    /**
     * 终止任务
     * @param jobName
     * @param buildNumber
     * @return
     * @throws IOException
     */
    public void abortedJob(String jobName, Long buildNumber) throws IOException {
        BuildWithDetails details = queryJobBuildDetail(jobName, buildNumber);
        details.Stop(true);
    }

    /**
     * 查询任务状态
     * @param jobName
     * @param buildVersion
     * @return
     * @throws IOException
     */
    public BuildResult queryJobStatus(String jobName, Long buildVersion) throws IOException {
        BuildWithDetails details = queryJobBuildDetail(jobName, buildVersion);
        return details.getResult();
    }

    /**
     * 查询任务详情
     * @param jobName
     * @return
     * @throws IOException
     */
    public BuildWithDetails queryJobBuildDetail(String jobName, Long buildNumber) throws IOException {
        JobWithDetails job = this.jenkinsServer.getJob(jobName);
        Build build = job.getBuildByNumber(buildNumber.intValue());
        return new CustomBuildWithDetails(build.details());
    }

    /**
     * 查询任务详情
     * @param jobName
     * @return
     * @throws IOException
     */
    public JobWithDetails queryJob(String jobName) throws IOException {
        return this.jenkinsServer.getJob(jobName);
    }

    /**
     * 流式写入控制台
     * @param response
     * @param jobName
     * @throws IOException
     * @throws InterruptedException
     */
    public void streamConsoleOutput(HttpServletResponse response, String jobName, Long buildNumber) throws IOException, InterruptedException {
        BuildWithDetails details = queryJobBuildDetail(jobName, buildNumber);
        CustomBuildWithDetails customBuildWithDetails = new CustomBuildWithDetails(details);
        customBuildWithDetails.streamConsoleOutput(new ConsoleStreamListener(response), 5, 60);
    }
}
