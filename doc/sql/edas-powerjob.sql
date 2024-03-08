create table powerjob_app_info
(
    id                    bigint auto_increment comment '主键id'
        primary key,
    app_id                bigint        default 0                 not null comment 'appId',
    app_code              varchar(64)   default ''                not null comment 'app编码',
    env                   varchar(8)    default 'dev'             not null comment '环境变量',
    powerjob_app_id       bigint        default 0                 not null comment 'powerjobAppId',
    powerjob_app_name     varchar(64)   default ''                not null comment 'powerjobAppName',
    powerjob_app_password varchar(128)  default ''                not null comment 'powerjobAppPassword',
    remark                varchar(200)  default ''                not null comment '备注',
    creator               varchar(64)                             not null comment '行记录创建者',
    modifier              varchar(64)                             not null comment '行记录最近更新人',
    created_time          timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time         timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version               bigint        default 0                 not null comment '行版本号',
    extension             varchar(2048) default ''                not null comment '拓展字段',
    deleted               bigint        default 0                 not null comment '是否删除',
    constraint unique_app
        unique (app_id, powerjob_app_id, deleted)
)
    comment 'powerjob app信息' charset = utf8mb4;

