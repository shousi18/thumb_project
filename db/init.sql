create table if not exists user
(
    id            bigint primary key not null auto_increment comment '主键id',
    username      varchar(256)       not null comment '用户昵称',
    user_account  varchar(256)       not null comment '用户账户',
    user_password varchar(256)       not null comment '用户密码',
    create_time   datetime default CURRENT_TIMESTAMP comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete     tinyint  default 0 comment '是否被删除(0-未被删除 1-被删除)'
) comment '用户表';

create table if not exists video
(
    id          bigint primary key not null auto_increment comment '主键id',
    thumb_count bigint             not null default 0 comment '视频点赞总数',
    create_time datetime                    default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime                    default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint                     default 0 comment '是否被删除(0-未被删除 1-被删除)'
) comment '视频表';

create table if not exists thumb
(
    id          bigint primary key not null auto_increment comment '主键id',
    user_id     bigint             not null comment '用户id',
    video_id    bigint             not null comment '视频id',
    create_time datetime default CURRENT_TIMESTAMP comment '创建时间'
) comment '点赞表';