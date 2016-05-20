# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table admins (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  created                   timestamp,
  constraint pk_admins primary key (id))
;

create table checks (
  id                        bigint not null,
  name                      varchar(255),
  result                    varchar(255),
  created                   timestamp,
  modified                  timestamp,
  constraint pk_checks primary key (id))
;

create sequence admins_seq;

create sequence checks_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists admins;

drop table if exists checks;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists admins_seq;

drop sequence if exists checks_seq;

