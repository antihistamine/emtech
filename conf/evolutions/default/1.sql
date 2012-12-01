# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table feeling (
  feeling_id                integer not null,
  check_in_date_time        timestamp,
  feeling                   varchar(255),
  user_user_id              integer,
  constraint pk_feeling primary key (feeling_id))
;

create table user (
  user_id                   integer not null,
  email                     varchar(255),
  password                  varchar(255),
  activity_level            integer,
  skill_level               integer,
  constraint pk_user primary key (user_id))
;

create sequence feeling_seq;

create sequence user_seq;

alter table feeling add constraint fk_feeling_user_1 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_feeling_user_1 on feeling (user_user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists feeling;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists feeling_seq;

drop sequence if exists user_seq;

