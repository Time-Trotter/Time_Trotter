# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table project (
  id                        bigint auto_increment not null,
  company_id                varchar(255),
  project_name              varchar(255),
  start_date                datetime,
  end_date                  datetime,
  billable                  tinyint(1) default 0,
  default_project           tinyint(1) default 0,
  active                    tinyint(1) default 0,
  constraint pk_project primary key (id))
;

create table task (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  assigned_to_id            integer,
  project_id                bigint,
  constraint pk_task primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  company_id                varchar(255),
  first_name                varchar(255),
  middle_name               varchar(255),
  last_name                 varchar(255),
  job_title                 varchar(255),
  reset_flag                tinyint(1) default 0,
  failed_login_attempt      integer,
  security_question         varchar(255),
  security_answer           varchar(255),
  create_date               datetime,
  modified_date             datetime,
  password_reset            tinyint(1) default 0,
  status                    tinyint(1) default 0,
  constraint pk_user primary key (id))
;


create table project_user (
  project_id                     bigint not null,
  user_id                        integer not null,
  constraint pk_project_user primary key (project_id, user_id))
;
alter table task add constraint fk_task_assignedTo_1 foreign key (assigned_to_id) references user (id) on delete restrict on update restrict;
create index ix_task_assignedTo_1 on task (assigned_to_id);
alter table task add constraint fk_task_project_2 foreign key (project_id) references project (id) on delete restrict on update restrict;
create index ix_task_project_2 on task (project_id);



alter table project_user add constraint fk_project_user_project_01 foreign key (project_id) references project (id) on delete restrict on update restrict;

alter table project_user add constraint fk_project_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table project;

drop table project_user;

drop table task;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

