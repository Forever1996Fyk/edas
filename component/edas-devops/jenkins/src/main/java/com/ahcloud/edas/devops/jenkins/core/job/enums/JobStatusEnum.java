package com.ahcloud.edas.devops.jenkins.core.job.enums;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/5 16:10
 **/
@Getter
public enum JobStatusEnum {

    /**
     * 任务状态
     */
    SUCCESS(0, "构建成功"),
    NOT_BUILT(1, "等待构建"),
    BUILDING(2, "构建中"),
    REBUILD(3, "重新构建"),
    FAILURE(4, "构建失败"),
    ABORTED(5, "已终止"),
    CANCELLED(6, "已取消"),
    ;
    private final int status;
    private final String desc;

    JobStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private final static List<JobStatusEnum> END_NODES = Lists.newArrayList(
            SUCCESS,FAILURE, ABORTED,CANCELLED
    );

    private final static List<JobStatusEnum> PROCESS_NODES = Lists.newArrayList(
            NOT_BUILT,BUILDING, REBUILD
    );

    private final static List<JobStatusEnum> ENABLE_ABORT_NODES = Lists.newArrayList(
            BUILDING, REBUILD
    );

    public static JobStatusEnum getByStatus(int status) {
        return Arrays.stream(values())
                .filter(jobStatusEnum -> jobStatusEnum.getStatus() == status)
                .findFirst()
                .orElse(null);
    }

    public boolean isEndNodes() {
        return END_NODES.contains(this);
    }

    public static List<Integer> getProcessNodes() {
        return Lists.newArrayList(
                NOT_BUILT.getStatus(),
                BUILDING.getStatus(),
                REBUILD.getStatus()
        );
    }

    public boolean enableAbort() {
        return ENABLE_ABORT_NODES.contains(this);
    }


}
