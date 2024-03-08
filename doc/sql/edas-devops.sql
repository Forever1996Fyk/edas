create table devops_config
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    app_id             bigint        default 0                 not null comment 'appId',
    app_code           varchar(64)   default ''                not null comment 'app编码',
    type               tinyint       default 1                 not null comment 'app类型',
    env                varchar(8)    default 'dev'             not null comment '环境变量',
    development_config varchar(2048) default ''                not null comment '部署配置',
    gitlab_config      varchar(2048) default ''                not null comment 'gitlab配置',
    jenkins_config     varchar(2048) default ''                not null comment 'jenkins配置',
    remark             varchar(200)  default ''                not null comment '备注',
    creator            varchar(64)                             not null comment '行记录创建者',
    modifier           varchar(64)                             not null comment '行记录最近更新人',
    created_time       timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time      timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version            bigint        default 0                 not null comment '行版本号',
    extension          varchar(2048) default ''                not null comment '拓展字段',
    deleted            bigint        default 0                 not null comment '是否删除',
    constraint uniq_app
        unique (app_id, app_code, env, deleted)
)
    comment 'app配置表';

create table devops_job_history
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    app_id             bigint        default 0                 not null comment 'appId',
    config             varchar(2048) default ''                not null comment 'devops配置',
    job_name           varchar(64)   default ''                not null comment '任务名称',
    params             varchar(2048) default ''                not null comment '任务参数',
    version            bigint        default 0                 not null comment '任务版本',
    description        varchar(512)  default ''                not null comment '任务描述',
    start_time         datetime                                null comment '执行时间',
    abort_time         datetime                                null comment '终止时间',
    end_time           datetime                                null comment '结束时间',
    duration           bigint        default 0                 not null comment '执行时长',
    estimated_duration bigint        default 0                 not null comment '预估时长',
    reason             varchar(1024) default ''                not null comment '原因',
    status             int           default 0                 not null comment '任务状态',
    remark             varchar(200)  default ''                not null comment '备注',
    creator            varchar(64)                             not null comment '行记录创建者',
    modifier           varchar(64)                             not null comment '行记录最近更新人',
    created_time       timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time      timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension          varchar(2048) default ''                not null comment '拓展字段',
    deleted            bigint        default 0                 not null comment '是否删除'
)
    comment 'devops历史任务表';

