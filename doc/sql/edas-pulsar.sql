create table pulsar_consumer_stats
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    tenant_id          bigint        default 0    not null comment '租户id',
    tenant_name        varchar(128)  default ''   not null comment '租户名称',
    namespace_id       bigint        default 0    not null comment '命名空间id',
    namespace_name     varchar(128)  default ''   not null comment '命名空间名称',
    topic_id           bigint        default 0    not null comment 'topicId',
    consumer_name      varchar(128)  default ''   not null comment '消费者名称',
    msg_rate_out       decimal(5, 2) default 0.00 not null comment '消息消费速率',
    msg_throughput_out decimal(5, 2) default 0.00 not null comment '消息消费大小',
    msg_rate_redeliver decimal(5, 2) default 0.00 not null comment '消息重新投递率',
    storage_size       bigint        default 0    not null comment '存储大小',
    time_stamp         bigint        default 0    not null comment '统计时间戳'
)
    comment 'pulsar消费者状态统计';

create table pulsar_namespace
(
    id             bigint auto_increment comment '主键id'
        primary key,
    tenant_id      bigint        default 0                 not null comment '租户id',
    tenant_name    varchar(128)  default ''                not null comment '租户名称',
    namespace_name varchar(128)  default ''                not null comment '命名空间名称',
    policies_json  varchar(2048) default ''                not null comment '命名空间策略json',
    description    varchar(512)  default ''                not null comment '说明',
    remark         varchar(200)  default ''                not null comment '备注',
    creator        varchar(64)                             not null comment '行记录创建者',
    modifier       varchar(64)                             not null comment '行记录最近更新人',
    created_time   timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time  timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension      varchar(2048) default ''                not null comment '拓展字段',
    version        bigint        default 0                 not null comment '行版本号',
    deleted        bigint        default 0                 not null comment '是否删除',
    constraint uniq_namespace
        unique (tenant_id, namespace_name, deleted)
)
    comment 'pulsar命名空间管理';

create table pulsar_producer_stats
(
    id                bigint auto_increment comment '主键id'
        primary key,
    tenant_id         bigint        default 0    not null comment '租户id',
    tenant_name       varchar(128)  default ''   not null comment '租户名称',
    namespace_id      bigint        default 0    not null comment '命名空间id',
    namespace_name    varchar(128)  default ''   not null comment '命名空间名称',
    topic_id          bigint        default 0    not null comment 'topicId',
    topic_name        varchar(128)  default ''   not null comment 'topic名称',
    producer_id       bigint        default 0    not null comment '生产者id',
    producer_name     varchar(128)  default ''   not null comment '生产者名称',
    msg_rate_in       decimal(5, 2) default 0.00 not null comment '消息接收速率',
    msg_throughput_in decimal(5, 2) default 0.00 not null comment '消息接收大小',
    average_msg_size  decimal(5, 2) default 0.00 not null comment '消息平均大小',
    storage_size      bigint        default 0    not null comment '存储大小',
    time_stamp        bigint        default 0    not null comment '统计时间戳'
)
    comment 'pulsar生产者状态统计';

create table pulsar_role_token
(
    id            bigint auto_increment comment '主键id'
        primary key,
    app_id        bigint        default 0                 not null comment 'appId',
    app_code      varchar(64)   default ''                not null comment 'app编码',
    env           varchar(8)    default ''                not null comment 'env',
    role          varchar(64)   default ''                not null comment '角色',
    token         varchar(512)  default ''                not null comment '认证token',
    remark        varchar(200)  default ''                not null comment '备注',
    creator       varchar(64)                             not null comment '行记录创建者',
    modifier      varchar(64)                             not null comment '行记录最近更新人',
    created_time  timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension     varchar(2048) default ''                not null comment '拓展字段',
    version       bigint        default 0                 not null comment '行版本号',
    deleted       bigint        default 0                 not null comment '是否删除',
    constraint uniq_pulsar_role
        unique (app_id, role, token, deleted)
)
    comment 'pulsar角色token管理';

create table pulsar_subscription
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    app_id             bigint        default 0                 not null comment 'appId',
    app_code           varchar(64)   default ''                not null comment 'app编码',
    env                varchar(8)    default ''                not null comment 'env',
    topic_id           bigint        default 0                 not null comment 'topicId',
    topic_name         varchar(128)  default ''                not null comment 'topic名称',
    subscription_name  varchar(128)  default ''                not null comment '订阅名称',
    is_auto_retry_dead tinyint(1)    default 0                 not null comment '是否自动创建重试和死信队列',
    description        varchar(512)  default ''                not null comment '说明',
    remark             varchar(200)  default ''                not null comment '备注',
    creator            varchar(64)                             not null comment '行记录创建者',
    modifier           varchar(64)                             not null comment '行记录最近更新人',
    created_time       timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time      timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension          varchar(2048) default ''                not null comment '拓展字段',
    version            bigint        default 0                 not null comment '行版本号',
    deleted            bigint        default 0                 not null comment '是否删除',
    constraint uniq_app_subscription
        unique (app_id, topic_id, subscription_name, deleted)
)
    comment 'pulsar subscription管理';

create table pulsar_subscription_stats
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    tenant_id          bigint        default 0    not null comment '租户id',
    tenant_name        varchar(128)  default ''   not null comment '租户名称',
    namespace_id       bigint        default 0    not null comment '命名空间id',
    namespace_name     varchar(128)  default ''   not null comment '命名空间名称',
    topic_id           bigint        default 0    not null comment 'topicId',
    subscription_id    bigint        default 0    not null comment 'subscriptionId',
    subscription_name  varchar(128)  default ''   not null comment 'subscription名称',
    msg_rate_out       decimal(5, 2) default 0.00 not null comment '消息消费速率',
    msg_throughput_out decimal(5, 2) default 0.00 not null comment '消息消费大小',
    msg_rate_redeliver decimal(5, 2) default 0.00 not null comment '消息重新投递率',
    msg_rate_expired   decimal(5, 2) default 0.00 not null comment '消息过期率',
    average_msg_size   decimal(5, 2) default 0.00 not null comment '消息平均大小',
    storage_size       bigint        default 0    not null comment '存储大小',
    time_stamp         bigint        default 0    not null comment '统计时间戳'
)
    comment 'pulsar订阅状态统计';

create table pulsar_tenant
(
    id               bigint auto_increment comment '主键id'
        primary key,
    tenant_name      varchar(128)  default ''                not null comment '租户名称',
    admin_roles      varchar(128)  default ''                not null comment '角色集合',
    allowed_clusters varchar(128)  default ''                not null comment '集群',
    type             tinyint       default 1                 not null comment '租户类型',
    remark           varchar(200)  default ''                not null comment '备注',
    creator          varchar(64)                             not null comment '行记录创建者',
    modifier         varchar(64)                             not null comment '行记录最近更新人',
    created_time     timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time    timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension        varchar(2048) default ''                not null comment '拓展字段',
    version          bigint        default 0                 not null comment '行版本号',
    deleted          bigint        default 0                 not null comment '是否删除',
    constraint uniq_tenant
        unique (tenant_name, deleted)
)
    comment 'pulsar租户管理';

create table pulsar_topic
(
    id              bigint auto_increment comment '主键id'
        primary key,
    app_id          bigint        default 0                 not null comment 'appId',
    app_code        varchar(64)   default ''                not null comment 'app编码',
    env             varchar(8)    default ''                not null comment 'env',
    tenant_id       bigint        default 0                 not null comment '租户id',
    tenant_name     varchar(128)  default ''                not null comment '租户名称',
    namespace_id    bigint        default 0                 not null comment '命名空间id',
    namespace_name  varchar(32)   default ''                not null comment '命名空间',
    topic_name      varchar(128)  default ''                not null comment 'topic名称',
    topic_full_name varchar(256)  default ''                not null comment 'topic全名',
    is_persistent   tinyint(1)    default 1                 not null comment '是否持久化',
    is_partition    tinyint(1)    default 1                 not null comment '是否分区',
    partition_num   tinyint       default 0                 not null comment '分区数',
    policies_json   varchar(2048) default ''                not null comment '策略',
    description     varchar(512)  default ''                not null comment '说明',
    source          tinyint       default 1                 not null comment '创建来源',
    remark          varchar(200)  default ''                not null comment '备注',
    creator         varchar(64)                             not null comment '行记录创建者',
    modifier        varchar(64)                             not null comment '行记录最近更新人',
    created_time    timestamp     default CURRENT_TIMESTAMP not null comment '行记录创建时间',
    modified_time   timestamp     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '行记录最近修改时间',
    extension       varchar(2048) default ''                not null comment '拓展字段',
    version         bigint        default 0                 not null comment '行版本号',
    deleted         bigint        default 0                 not null comment '是否删除'
)
    comment 'pulsar topic管理';

create table pulsar_topic_stats
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    tenant_id          bigint        default 0    not null comment '租户id',
    tenant_name        varchar(128)  default ''   not null comment '租户名称',
    namespace_id       bigint        default 0    not null comment '命名空间id',
    namespace_name     varchar(128)  default ''   not null comment '命名空间名称',
    topic_id           bigint        default 0    not null comment 'topicId',
    topic_name         varchar(128)  default ''   not null comment 'topic名称',
    bundle             varchar(128)  default ''   not null comment '绑定',
    persistent         varchar(128)  default ''   not null comment '持久化',
    msg_rate_in        decimal(5, 2) default 0.00 not null comment '消息接收速率',
    msg_rate_out       decimal(5, 2) default 0.00 not null comment '消息消费速率',
    msg_throughput_in  decimal(5, 2) default 0.00 not null comment '消息接收大小',
    msg_throughput_out decimal(5, 2) default 0.00 not null comment '消息消费大小',
    average_msg_size   decimal(5, 2) default 0.00 not null comment '消息平均大小',
    storage_size       bigint        default 0    not null comment '存储大小',
    msg_in_counter     bigint        default 0    not null comment '发布到topic的总消息数',
    msg_out_counter    bigint        default 0    not null comment '传递给消费者的消息总数',
    bytes_in_counter   bigint        default 0    not null comment '发布到topic的总字节数',
    bytes_out_counter  bigint        default 0    not null comment 'topic接收的总字节数',
    time_stamp         bigint        default 0    not null comment '统计时间戳'
)
    comment 'pulsarTopic状态统计';

