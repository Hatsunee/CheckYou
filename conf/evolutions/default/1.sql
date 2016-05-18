# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table check (
  id                        bigint not null,
  name                      varchar(255),
  result                    varchar(255),
  created                   timestamp,
  modified                  timestamp,
  constraint pk_check primary key (id))
;

create sequence check_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists check;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists check_seq;

