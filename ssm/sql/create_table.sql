drop table if exists T_USER;

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table T_USER
(
   id                   integer not null comment '主键',
   name                 varchar(32) not null comment '用户名',
   password             varchar(32) not null comment '密码',
   age                  integer comment '年龄',
   primary key (id)
);

alter table T_USER comment '用户表';
