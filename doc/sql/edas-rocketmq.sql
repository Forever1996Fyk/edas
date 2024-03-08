create table rmq_app_config
(
    id            bigint auto_increment comment '主键id'
        primary key,
    app_id        bigint        default 0                 not null comment 'appId',
    app_code      varchar(64)   default ''                not null comment 'app编码',
    env           varchar(8)    default ''                not null comment 'env',
    access_key    varchar(128)  default ''                not null comment 'access_key',
    secret_key    varchar(128)  default ''                not null comment 'secret_key',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    version       bigint        default 0                 not null comment '行版本号',
    deleted       bigint        default 0                 not null comment '是否删除',
    constraint uniq_rmq_app
        unique (app_id, deleted)
)
    comment 'rocketmq配置';

create table rmq_subscribe_acl
(
    id            bigint auto_increment comment '主键id'
        primary key,
    app_id        bigint        default 0                 not null comment 'appId',
    app_code      varchar(64)   default ''                not null comment 'app编码',
    env           varchar(8)    default ''                not null comment 'env',
    broker_names  varchar(256)  default ''                not null comment 'brokers',
    group_name    varchar(128)  default ''                not null comment '消费组名称',
    topic_id      bigint        default 0                 not null comment '需订阅的topicId',
    topic_perm    varchar(16)   default ''                not null comment 'topic权限',
    group_perm    varchar(16)   default ''                not null comment 'group权限',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    version       bigint        default 0                 not null comment '行版本号',
    deleted       bigint        default 0                 not null comment '是否删除'
)
    comment 'rocketmq订阅组权限';

create table rmq_topic
(
    id              bigint auto_increment comment '主键id'
        primary key,
    app_id          bigint        default 0                 not null comment 'appId',
    app_code        varchar(64)   default ''                not null comment 'app编码',
    env             varchar(8)    default ''                not null comment 'env',
    broker_names    varchar(256)  default ''                not null comment 'broker名称列表',
    topic_name      varchar(128)  default ''                not null comment 'topic名称',
    write_queue_num tinyint       default 1                 not null comment '写队列数',
    read_queue_num  tinyint       default 1                 not null comment '读队列数',
    perm            tinyint       default 1                 not null comment '权限',
    creator         varchar(64)                             not null comment '行记录创建者',
    modifier        varchar(64)                             not null comment '行记录最近更新人',
    created_time    timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time   timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension       varchar(2048) default ''                not null comment '拓展字段',
    version         bigint        default 0                 not null comment '行版本号',
    deleted         bigint        default 0                 not null comment '是否删除',
    constraint uniq_topic
        unique (topic_name, deleted)
)
    comment 'rocketmq topic配置';

