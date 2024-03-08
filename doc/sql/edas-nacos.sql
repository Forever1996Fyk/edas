create table nacos_config
(
    id            bigint auto_increment comment '主键id'
        primary key,
    app_id        bigint        default 0                 not null comment 'appId',
    app_code      varchar(64)   default ''                not null comment 'app编码',
    env           varchar(8)    default 'dev'             not null comment '环境变量',
    tenant        varchar(64)   default ''                not null comment '租户',
    data_id       varchar(128)  default ''                not null comment '配置id',
    biz_group     varchar(64)   default ''                not null comment '配置分组',
    type          varchar(16)   default ''                not null comment '配置类型',
    description   varchar(512)  default ''                not null comment '配置描述',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    version       bigint        default 0                 not null comment '行版本号',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    deleted       bigint        default 0                 not null comment '是否删除',
    constraint unique_nacos_app
        unique (app_id, deleted) comment '唯一app配置'
)
    comment 'nacos配置表' charset = utf8mb4;

